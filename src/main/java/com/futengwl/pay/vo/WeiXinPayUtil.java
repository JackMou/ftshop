package com.futengwl.pay.vo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class WeiXinPayUtil {

	/**
	 * 生成随机数字符串
	 * @return
	 */
	public static String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	
	/**
	 * 将微信字符串返回值以Map键值对形式返回给前端，方便其调用
	 * @param content
	 * @return
	 */
	public static Map<String, String> decodeXml(String content) {
		try {
			Map<String, String> xml = new HashMap<String, String>(32);
			XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				String nodeName = parser.getName();
				switch (event) {
					case XmlPullParser.START_DOCUMENT:	
						break;
					case XmlPullParser.START_TAG:	
						if ("xml".equals(nodeName) == false) {
							xml.put(nodeName, parser.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						break;
					default: break;
				}
				event = parser.next();
			}
			return xml;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String xmlStr = "<xml><return_code><![CDATA[SUCCESS]]></return_code>\n" +
				"<return_msg><![CDATA[OK]]></return_msg>\n" +
				"<appid><![CDATA[wxd9611f15871de9e4]]></appid>\n" +
				"<mch_id><![CDATA[1401718002]]></mch_id>\n" +
				"<nonce_str><![CDATA[fiTHqBJVPYFML1G2]]></nonce_str>\n" +
				"<sign><![CDATA[F109724562B9499E7B703159DC84C862]]></sign>\n" +
				"<result_code><![CDATA[SUCCESS]]></result_code>\n" +
				"<transaction_id><![CDATA[4001422001201704106551898830]]></transaction_id>\n" +
				"<out_trade_no><![CDATA[117041019345629736214]]></out_trade_no>\n" +
				"<out_refund_no><![CDATA[1170410193456297362141491824332624]]></out_refund_no>\n" +
				"<refund_id><![CDATA[50000602382017041000931855756]]></refund_id>\n" +
				"<refund_channel><![CDATA[]]></refund_channel>\n" +
				"<refund_fee>450</refund_fee>\n" +
				"<coupon_refund_fee>0</coupon_refund_fee>\n" +
				"<total_fee>450</total_fee>\n" +
				"<cash_fee>450</cash_fee>\n" +
				"<coupon_refund_count>0</coupon_refund_count>\n" +
				"<cash_refund_fee>450</cash_refund_fee>\n" +
				"</xml>";
		System.out.println(decodeXml(xmlStr)); // 有BUG
	}
	
	/**
	 * 生成微信签名字符串
	 * @param params
	 * @param appKey
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String genPackageSign(List<BasicNameValuePair> params,String appKey) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();		
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(appKey);		
		String packageSign = MD5.getMessageDigest(sb.toString().getBytes("utf-8")).toUpperCase();
		return packageSign;
	}
	
	/** 
     * 微信支付签名算法sign 
     * @param appKey
     * @param parameters 
     * @return 
	 * @throws UnsupportedEncodingException 
     */
    public static String createSign(SortedMap<Object,Object> parameters,String appKey) throws UnsupportedEncodingException{  
        StringBuffer sb = new StringBuffer();
        //所有参与传参的参数按照accsii排序（升序）  
        Set es = parameters.entrySet();
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + appKey);
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes("utf-8")).toUpperCase();
        return packageSign;
    }  
	
	/**
	 * 将集合中的数据转换为字符串
	 * @param params
	 * @return
	 */
	public static String toXml(List<BasicNameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<"+params.get(i).getName()+">");
			sb.append(params.get(i).getValue());
			sb.append("</"+params.get(i).getName()+">");
		}
		sb.append("</xml>");
		return sb.toString();
	}
}
