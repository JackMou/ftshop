package com.futengwl.pay;

import com.futengwl.pay.vo.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by xiongzhe on 2017/3/31.
 */
@Component("weixinClientUtil")
public class WeixinClientUtil {
    public static Logger logger = LoggerFactory.getLogger(WeixinClientUtil.class);
    /**
     * 微信退款接口
     */
    public static final String API_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    /**
     * 退款查询接口
     */
    public static final String API_REFUND_QUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";

    public static final Properties prop = ConfigPropertiesUtils.getProperties();
//    static {
//        try {
//            prop = PropertiesUtils.loadProperties("weixinPay.properties");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * APP申请退款，并将返回值Map对象返回
     * @param transactionId
     * @param out_trade_no
     * @param outRequestNo
     * @param total_fee
     * @param refund_fee
     * @param refundReason
     * @param orderSrc
     * @return
     */
    public Map<String, String> weixinRefund(String transactionId, String out_trade_no, String outRequestNo, int total_fee, int refund_fee, String refundReason, String orderSrc) {
        if (StringUtils.isEmpty(outRequestNo)) {
            outRequestNo = out_trade_no + System.currentTimeMillis();
        }
        try {
            String mchId = prop.getProperty("MCH_ID");
            String certificatePath = prop.getProperty("APP_CERTIFICATE_PATH");
            if (ConstantUtils.ORDER_SRC.WEIXIN.name().equalsIgnoreCase(orderSrc) || "WAP".equalsIgnoreCase(orderSrc)) { //微信下单--wap微信->公众号
                mchId = prop.getProperty("PUB_MCH_ID");
                certificatePath = prop.getProperty("PUB_CERTIFICATE_PATH");
            }

            String entity = getApplyRefundArgs(transactionId, out_trade_no, outRequestNo, total_fee, refund_fee, refundReason, orderSrc);
            byte[] buf = UtilRefund.httpPost(API_REFUND_URL, entity, certificatePath, mchId);
            String content = new String(buf, "UTF-8");
            logger.debug("content==" + content);
            Map<String, String> xml = WeiXinPayUtil.decodeXml(content);
            return xml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * APP申请退款请求参数封装，返回值为字符串
     * @param out_trade_no
     * @param out_refund_no
     * @param total_fee
     * @param refund_fee
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    private String getApplyRefundArgs(String transactionId, String out_trade_no, String out_refund_no,
                                      int total_fee, int refund_fee, String refundReason, String orderSrc) throws IOException {
        String appId = prop.getProperty("APP_ID");
        String mchId = prop.getProperty("MCH_ID");
        String appKey = prop.getProperty("API_KEY");
        if (ConstantUtils.ORDER_SRC.WEIXIN.name().equalsIgnoreCase(orderSrc) || "WAP".equalsIgnoreCase(orderSrc)) { //微信下单
            appId = prop.getProperty("PUB_APP_ID");
            mchId = prop.getProperty("PUB_MCH_ID");
            appKey = prop.getProperty("PUB_API_KEY");
        }
        try {
            String nonceStr = WeiXinPayUtil.genNonceStr();
            List<BasicNameValuePair> packageParams = new LinkedList<BasicNameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", appId));
            packageParams.add(new BasicNameValuePair("mch_id", mchId));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            //packageParams.add(new BasicNameValuePair("op_user_id", mchId));
            packageParams.add(new BasicNameValuePair("out_refund_no", out_refund_no));
            ////支付添加随机数--packageParams.add(new BasicNameValuePair("out_trade_no", out_trade_no));
            packageParams.add(new BasicNameValuePair("refund_desc", StringUtils.defaultIfBlank(refundReason, "DEFAULT")));
            packageParams.add(new BasicNameValuePair("refund_fee", String.valueOf(refund_fee)));
            packageParams.add(new BasicNameValuePair("total_fee", String.valueOf(total_fee)));
            packageParams.add(new BasicNameValuePair("transaction_id", transactionId));
            String sign = WeiXinPayUtil.genPackageSign(packageParams, appKey);
            packageParams.add(new BasicNameValuePair("sign", sign));
            return WeiXinPayUtil.toXml(packageParams);
        } catch (Exception e) {
            logger.info(e.toString());
            return null;
        }
    }


    /**
     *  APP查询退款，并将返回值Map对象返回
     * @param transactionId 微信订单号
     * @param out_refund_no 商户退款单号
     * @return
     * @throws IOException
     */
    public Map<String, String> queryRefundInfo(String transactionId, String out_refund_no, String orderSrc) throws IOException {
        String entity = this.getQueryRefundArgs(transactionId, out_refund_no, orderSrc);
        byte[] buf = Util.httpPost(API_REFUND_QUERY_URL, entity);
        String content = new String(buf, "UTF-8");
        return WeiXinPayUtil.decodeXml(content);
    }
    /**
     * APP查询退款请求参数封装，返回值为字符串
     * @param transactionId
     * @param out_refund_no
     * @return
     */
    private String getQueryRefundArgs(String transactionId, String out_refund_no, String orderSrc) {
        try {
            String appId = prop.getProperty("APP_ID");
            String mchId = prop.getProperty("MCH_ID");
            String appKey = prop.getProperty("API_KEY");
            if (ConstantUtils.ORDER_SRC.WEIXIN.name().equalsIgnoreCase(orderSrc) || "WAP".equalsIgnoreCase(orderSrc)) { //微信下单
                appId = prop.getProperty("PUB_APP_ID");
                mchId = prop.getProperty("PUB_MCH_ID");
                appKey = prop.getProperty("PUB_API_KEY");
            }
            String nonceStr = WeiXinPayUtil.genNonceStr();
            List<BasicNameValuePair> packageParams = new LinkedList<BasicNameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", appId));
            packageParams.add(new BasicNameValuePair("mch_id", mchId));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            if (StringUtils.isNotEmpty(out_refund_no)) {
                packageParams.add(new BasicNameValuePair("out_refund_no", out_refund_no));
            }
            if (StringUtils.isNotEmpty(transactionId)) {
                packageParams.add(new BasicNameValuePair("transaction_id", transactionId));
            }
//            if (StringUtils.isNotEmpty(out_trade_no)) {
//                packageParams.add(new BasicNameValuePair("out_trade_no", out_trade_no));
//            }
            String sign1 = WeiXinPayUtil.genPackageSign(packageParams, appKey);
            packageParams.add(new BasicNameValuePair("sign", sign1));
            String xmlString = WeiXinPayUtil.toXml(packageParams);
            return xmlString;
        } catch (Exception e) {
            logger.info(e.toString());
            return null;
        }
    }

    /**
     * 支付订单查询request字符串
     * @param transactionId
     * @param out_trade_no
     * @param orderSrc
     * @return
     */
    public static String getOrderQueryRequestStr(String transactionId, String out_trade_no, String orderSrc) {
        String appId = prop.getProperty("APP_ID");
        String mchId = prop.getProperty("MCH_ID");
        String appKey = prop.getProperty("API_KEY");
        if (ConstantUtils.ORDER_SRC.WEIXIN.name().equalsIgnoreCase(orderSrc) || "WAP".equalsIgnoreCase(orderSrc)) { //微信下单
            appId = prop.getProperty("PUB_APP_ID");
            mchId = prop.getProperty("PUB_MCH_ID");
            appKey = prop.getProperty("PUB_API_KEY");
        }
        try {
            String nonceStr = WeiXinPayUtil.genNonceStr();
            List<BasicNameValuePair> packageParams = new LinkedList<BasicNameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", appId));
            packageParams.add(new BasicNameValuePair("mch_id", mchId));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            //packageParams.add(new BasicNameValuePair("out_trade_no", out_trade_no));
            packageParams.add(new BasicNameValuePair("transaction_id", transactionId));
            packageParams.add(new BasicNameValuePair("sign", WeiXinPayUtil.genPackageSign(packageParams, appKey)));
            return WeiXinPayUtil.toXml(packageParams);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 公众号支付生成ACCESS_TOKEN接口
     */
    public static com.alibaba.fastjson.JSONObject getAccessToken(String code) throws Exception {
        //prop.load(new InputStreamReader(this.getClass().getResourceAsStream("/weixinPay.properties"), "UTF-8"));
        String appId = prop.getProperty("PUB_APP_ID");
        String secret = prop.getProperty("PUB_SECRET");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + secret
                + "&code=" + code + "&grant_type=authorization_code";
        String message = "";
        com.alibaba.fastjson.JSONObject retJson = null;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            message = new String(jsonBytes, "UTF-8");
            retJson = com.alibaba.fastjson.JSONObject.parseObject(message);
            System.out.println("message==" + message);
            //retJson = new JSONObject(message);
            System.out.println("retJson======>" + retJson);
            //System.out.println("openid==" + openid);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retJson;
    }


    //企业付款业务
    public static Map<String, String> sendTransfersRequest(int transferFee, String tradeNo, String desc, String openid, String realName, String spbillCreateIp) throws Exception {
        String url = String.format("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
        String requestXML = genTransfersRequestXML(transferFee, tradeNo, desc, openid, realName, spbillCreateIp);
        String filePath = prop.getProperty("PUB_CERTIFICATE_PATH");
        String mchId = prop.getProperty("PUB_MCH_ID");
        byte[] buf = UtilRefund.httpPost(url, requestXML, filePath, mchId);
        String content = new String(buf, "UTF-8");
        logger.debug("content==" + content);
        Map<String, String> xml = WeiXinPayUtil.decodeXml(content);
        System.out.println("xml==" + xml);
        if(xml==null){
           xml = new HashMap<>();
           xml.put("return_code","ERROR");
        }
        return xml;
    }
    //企业付款业务
    public static String genTransfersRequestXML(int transferFee, String tradeNo, String desc, String openid, String realName, String spbillCreateIp) throws Exception {
        //Properties prop = wechatConfig;
        String appId = prop.getProperty("PUB_APP_ID");
        String mchId = prop.getProperty("PUB_MCH_ID");
        String appKey = prop.getProperty("PUB_API_KEY");
        try {
            String nonceStr = genNonceStr();
            List<BasicNameValuePair> packageParams = new LinkedList<BasicNameValuePair>();
            packageParams.add(new BasicNameValuePair("amount", String.valueOf(transferFee)));
            packageParams.add(new BasicNameValuePair("check_name", "NO_CHECK"));
            packageParams.add(new BasicNameValuePair("desc", desc));
            packageParams.add(new BasicNameValuePair("mch_appid", appId));
            packageParams.add(new BasicNameValuePair("mchid", mchId));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("openid", openid));
            packageParams.add(new BasicNameValuePair("partner_trade_no", tradeNo));
            packageParams.add(new BasicNameValuePair("re_user_name", realName));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", spbillCreateIp));
            String sign = WeiXinPayUtil.genPackageSign(packageParams, appKey);
            packageParams.add(new BasicNameValuePair("sign", sign));
            String xmlstring = WeiXinPayUtil.toXml(packageParams);
            return xmlstring;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取用户真实IP地址
     * @param request
     * @return
     */
    public static String getRemoteHost(HttpServletRequest request){
//        Enumeration<String> test =  request.getHeaderNames();
//        while (test.hasMoreElements()) {
//            String key = test.nextElement();
//            System.out.println(key + "=" +request.getHeader(key));
//        }
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }
    public static String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes(Charset.forName("UTF-8")));
    }
}
