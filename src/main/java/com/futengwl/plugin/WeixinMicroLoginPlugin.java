/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 0BE9+6iYKnR66SE7QnlcF/+/eFCZmU44
 */
package com.futengwl.plugin;

import com.alibaba.fastjson.JSON;
import com.futengwl.util.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Plugin - 微信登录(小程序登录)
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Component("weixinMicroLoginPlugin")
public class WeixinMicroLoginPlugin extends LoginPlugin {

    /**
     * openId请求URL
     */
    private static final String OPEN_ID_REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session";

	@Override
	public String getName() {
		return "微信登录(小程序登录)";
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
		return "/admin/plugin/weixin_micro_login/install";
	}

	@Override
	public String getUninstallUrl() {
		return "/admin/plugin/weixin_micro_login/uninstall";
	}

	@Override
	public String getSettingUrl() {
		return "/admin/plugin/weixin_micro_login/setting";
	}

	@Override
	public boolean supports(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT");
		return StringUtils.containsIgnoreCase(userAgent, "miniprogram");
	}

	@Override
	public void signInHandle(LoginPlugin loginPlugin, String extra, HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
	}

	@Override
	public boolean isSignInSuccess(LoginPlugin loginPlugin, String extra, HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		if (StringUtils.isEmpty(code)) {
			return false;
		}
        Map map = new HashMap<String,String>();
        map.put("appid",getAppId());
        map.put("secret",getAppSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String returnStr=WebUtils.get(OPEN_ID_REQUEST_URL,map);
        Map result = JSON.parseObject(returnStr);
        String openId = (String) result.get("openid");
        if (StringUtils.isNotEmpty(openId)) {
            request.setAttribute("openid", openId);
            return true;
        }
        return false;

//		try {
//			CloseableHttpClient httpclient = HttpClients.createDefault();
//
//			URI uri = new URIBuilder()
//					.setScheme("https")
//					.setHost("api.weixin.qq.com")
//					.setPath("/sns/jscode2session")
//					.setParameter("appid", getAppId())
//					.setParameter("secret", getAppSecret())
//					.setParameter("js_code", code)
//					.setParameter("grant_type", "authorization_code")
//					.build();
//			HttpGet httpget = new HttpGet(uri);
//
//			CloseableHttpResponse response1 = httpclient.execute(httpget);
//			try {
//				HttpEntity entity = response1.getEntity();
//				if (entity != null) {
//					InputStream instream = entity.getContent();
//					try {
//						BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
//						StringBuilder sb = new StringBuilder();
//						String line;
//						while ((line = reader.readLine()) != null) {
//							sb.append(line);
//						}
//						Map returnMsg = JSON.parseObject(sb.toString());
//						String openId = (String) returnMsg.get("openid");
//						if (StringUtils.isNotEmpty(openId)) {
//							request.setAttribute("openid", openId);
//							return true;
//						}
//					} finally {
//						instream.close();
//					}
//				}
//			}catch(Exception ex){
//
//			}finally {
//				response1.close();
//			}
//		}catch (Exception ex) {
//			throw new RuntimeException(ex.getMessage(), ex);
//		}
//		return false;
	}

	@Override
	public String getUniqueId(HttpServletRequest request) {
		return (String) request.getAttribute("openid");
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

}