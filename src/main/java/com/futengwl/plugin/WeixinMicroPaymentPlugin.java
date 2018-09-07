/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: VthNmgBQmU/zm2tpvkujw6lmAcjv2FX0
 */
package com.futengwl.plugin;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.futengwl.entity.PaymentTransaction;
import com.futengwl.util.WebUtils;
import com.futengwl.util.XmlUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest.TokenRequestBuilder;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Plugin - 微信支付(微信小程序支付)
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Component("weixinMicroPaymentPlugin")
public class WeixinMicroPaymentPlugin extends PaymentPlugin {

	/**
	 * openId请求URL
	 */
	private static final String OPEN_ID_REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session";

	/**
	 * prepay_id请求URL
	 */
	private static final String PREPAY_ID_REQUEST_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	/**
	 * 查询订单请求URL
	 */
	private static final String ORDER_QUERY_REQUEST_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

	/**
	 * 微信版本配比
	 */
	private static final Pattern WEIXIN_VERSION_PATTERN = Pattern.compile("MicroMessenger/(?<majorVersion>\\d+)(\\.(?<minorVersion>\\d+))?", Pattern.CASE_INSENSITIVE);

	@Override
	public String getName() {
		return "微信支付(小程序支付)";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getAuthor() {
		return "FTSHOP";
	}

	@Override
	public String getSiteUrl() {
		return "http://www.futengwl.com";
	}

	@Override
	public String getInstallUrl() {
		return "/admin/plugin/weixin_micro_payment/install";
	}

	@Override
	public String getUninstallUrl() {
		return "/admin/plugin/weixin_micro_payment/uninstall";
	}

	@Override
	public String getSettingUrl() {
		return "/admin/plugin/weixin_micro_payment/setting";
	}

	@Override
	public boolean supports(HttpServletRequest request) {
        String userAgent = request.getHeader("USER-AGENT");
        return StringUtils.containsIgnoreCase(userAgent, "miniprogram");
	}

	@Override
	public void prePayHandle(PaymentPlugin paymentPlugin, PaymentTransaction paymentTransaction, String paymentDescription, String extra, HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception {

        modelAndView.addObject("requestUrl", getPayUrl(paymentPlugin, paymentTransaction));
        modelAndView.setViewName("shop/plugin/weixin_micro_payment/pre_pay");
	}

	@Override
	public void payHandle(PaymentPlugin paymentPlugin, PaymentTransaction paymentTransaction, String paymentDescription, String extra, HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception {
		String openId = request.getParameter("openid");
        String code = request.getParameter("code");
        if (StringUtils.isEmpty(openId)&&StringUtils.isEmpty(code)) {
            modelAndView.setViewName("common/error/unprocessable_entity");
            return;
        }
        if (StringUtils.isEmpty(openId)) {
            openId=getOpenId(code);
        }


		Map<String, Object> parameterMap = new TreeMap<>();
		parameterMap.put("appid", getAppId());
		parameterMap.put("mch_id", getMchId());
		parameterMap.put("nonce_str", DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30)));
		parameterMap.put("body", StringUtils.abbreviate(paymentDescription.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5 ]", StringUtils.EMPTY), 600));
		parameterMap.put("out_trade_no", paymentTransaction.getSn());
		parameterMap.put("total_fee", String.valueOf(paymentTransaction.getAmount().multiply(new BigDecimal(100)).setScale(0)));
		parameterMap.put("spbill_create_ip", request.getLocalAddr());
		parameterMap.put("notify_url", getPostPayUrl(paymentPlugin, paymentTransaction));
		parameterMap.put("trade_type", "JSAPI");
		parameterMap.put("openid", openId);
		parameterMap.put("sign", generateSign(parameterMap));

		String result = WebUtils.post(PREPAY_ID_REQUEST_URL, XmlUtils.toXml(parameterMap));
		Map<String, String> resultMap = XmlUtils.toObject(result, new TypeReference<Map<String, String>>() {
		});

		String prepayId = resultMap.get("prepay_id");
		String tradeType = resultMap.get("trade_type");
		String resultCode = resultMap.get("result_code");

		if (StringUtils.equals(tradeType, "JSAPI") && StringUtils.equals(resultCode, "SUCCESS")) {
			Map<String, Object> modelMap = new TreeMap<>();
			modelMap.put("appId", getAppId());
			modelMap.put("timeStamp", new Date().getTime());
			modelMap.put("nonceStr", DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30)));
			modelMap.put("package", "prepay_id=" + prepayId);
			modelMap.put("signType", "MD5");
			modelMap.put("paySign", generateSign(modelMap));
			modelMap.put("postPayUrl", getPostPayUrl(paymentPlugin, paymentTransaction));
			modelAndView.addAllObjects(modelMap);
			modelAndView.setViewName("shop/plugin/weixin_micro_payment/pay");
		}
	}

	@Override
	public void postPayHandle(PaymentPlugin paymentPlugin, PaymentTransaction paymentTransaction, String paymentDescription, String extra, boolean isPaySuccess, HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception {
		String xml = IOUtils.toString(request.getInputStream(), "UTF-8");
		if (StringUtils.isNotEmpty(xml)) {
			Map<String, String> resultMap = XmlUtils.toObject(xml, new TypeReference<Map<String, String>>() {
			});
			if (StringUtils.equals(resultMap.get("return_code"), "SUCCESS")) {
				OutputStream outputStream = response.getOutputStream();
				IOUtils.write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>", outputStream, "UTF-8");
				outputStream.flush();
			} else {
				super.postPayHandle(paymentPlugin, paymentTransaction, paymentDescription, extra, isPaySuccess, request, response, modelAndView);
			}
		} else {
			super.postPayHandle(paymentPlugin, paymentTransaction, paymentDescription, extra, isPaySuccess, request, response, modelAndView);
		}
	}

	@Override
	public boolean isPaySuccess(PaymentPlugin paymentPlugin, PaymentTransaction paymentTransaction, String paymentDescription, String extra, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> parameterMap = new TreeMap<>();
		parameterMap.put("appid", getAppId());
		parameterMap.put("mch_id", getMchId());
		parameterMap.put("out_trade_no", paymentTransaction.getSn());
		parameterMap.put("nonce_str", DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30)));
		parameterMap.put("sign", generateSign(parameterMap));
		String result = WebUtils.post(ORDER_QUERY_REQUEST_URL, XmlUtils.toXml(parameterMap));
		Map<String, String> resultMap = XmlUtils.toObject(result, new TypeReference<Map<String, String>>() {
		});
		return StringUtils.equals(resultMap.get("return_code"), "SUCCESS") && StringUtils.equals(resultMap.get("result_code"), "SUCCESS") && StringUtils.equals(resultMap.get("trade_state"), "SUCCESS")
				&& paymentTransaction.getAmount().multiply(new BigDecimal(100)).compareTo(new BigDecimal(resultMap.get("total_fee"))) == 0;
	}

	/**
	 * 获取AppID
	 * 
	 * @return AppID
	 */
	private String getAppId() {
		return getAttribute("appId");
	}

	/**
	 * 获取AppSecret
	 * 
	 * @return AppSecret
	 */
	private String getAppSecret() {
		return getAttribute("appSecret");
	}

	/**
	 * 获取商户号
	 * 
	 * @return 商户号
	 */
	private String getMchId() {
		return getAttribute("mchId");
	}

	/**
	 * 获取API密钥
	 * 
	 * @return API密钥
	 */
	private String getApiKey() {
		return getAttribute("apiKey");
	}

	/**
	 * 获取OpenID
	 * 
	 * @param code
	 *            code值
	 * @return OpenID
	 */
	private String getOpenId(String code) {

        Map map = new HashMap<String,String>();
        map.put("appid",getAppId());
        map.put("secret",getAppSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String returnStr=WebUtils.get(OPEN_ID_REQUEST_URL,map);
        Map result = JSON.parseObject(returnStr);
        String openId = (String) result.get("openid");
        if (StringUtils.isNotEmpty(openId)) {
            return openId;
        }
        return null;
	}

	/**
	 * 生成签名
	 * 
	 * @param parameterMap
	 *            参数
	 * @return 签名
	 */
	private String generateSign(Map<String, ?> parameterMap) {
		return StringUtils.upperCase(DigestUtils.md5Hex(joinKeyValue(new TreeMap<>(parameterMap), null, "&key=" + getApiKey(), "&", true)));
	}

}