package com.futengwl.pay.wechat;


import com.google.common.collect.Lists;
import com.futengwl.pay.WeixinClientUtil;
import com.futengwl.pay.vo.MD5;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by xiongzhe on 2017/4/1.
 */
public class Signature {
    private static final Logger logger = LoggerFactory.getLogger(Signature.class);
    public static final String AND_OPR = "&";
    /**
     * (ConstantsKey.WechatConfig.WECHAT_API_SECURE_KEY);;
     */
    public static final String WECHAT_API_SECURE_KEY = WeixinClientUtil.prop.getProperty("API_KEY");
    public static final String WECHAT_GZH_API_SECURE_KEY = WeixinClientUtil.prop.getProperty("PUB_API_KEY");
    /**
     * 签名算法,对象
     *
     * @param o 要参与签名的数据对象
     * @return 签名
     * @throws IllegalAccessException
     */
    public static String getSign(Object o, boolean isGZH) throws IllegalAccessException {
        ArrayList<String> list = Lists.newArrayList();
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.get(o) != null && f.get(o) != "") {
                list.add(f.getName() + "=" + f.get(o) + AND_OPR);
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        String key = WECHAT_API_SECURE_KEY;
        if (isGZH) {
            key = WECHAT_GZH_API_SECURE_KEY;
        }
        result += "key=" + key;
        logger.info("Sign Before MD5:" + result);
        result = MD5.MD5Encode(result, null).toUpperCase();
        logger.info("Sign Result:" + result);
        return result;
    }
    /**
     * 签名算法
     *
     * @param map
     * @return
     */
    public static String getSign(Map<String, Object> map, boolean isGZH) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + AND_OPR);
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        logger.info("Sign Before MD5:" + result);
        String key = WECHAT_API_SECURE_KEY;
        if (isGZH) {
            key = WECHAT_GZH_API_SECURE_KEY;
        }
        result += "key=" + key;
        logger.info("Sign Result:" + result);
        result = MD5.MD5Encode(result, null).toUpperCase();
        return result;
    }
    /**
     * 从API返回的XML数据里面重新计算一次签名
     *
     * @param responseString API返回的XML数据
     * @return 新鲜出炉的签名
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static String getSignFromResponseString(String responseString, boolean isGZH) throws IOException, SAXException, ParserConfigurationException, IllegalAccessException {
        Map<String, String> map = XMLParser.getMapFromXML(responseString);
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign", "");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        return Signature.getSign(map, isGZH);
    }
    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     *
     * @param responseString API返回的XML数据字符串
     * @return API签名是否合法
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static boolean checkIsSignValidFromResponseString(String responseString, boolean isGZH) throws ParserConfigurationException, IOException, SAXException, IllegalAccessException {
        Map<String, String> map = XMLParser.getMapFromXML(responseString);
        logger.info(map.toString());
        String signFromAPIResponse = map.get("sign").toString();
        if (StringUtils.isBlank(signFromAPIResponse)) {
            logger.info("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign", "");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = Signature.getSign(map, isGZH);
        if (StringUtils.equals(signForAPIResponse, signFromAPIResponse)) {
            //签名验不过，表示这个API返回的数据有可能已经被篡改了
            logger.info("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
            return false;
        }
        logger.info("恭喜，API返回的数据签名验证通过!!!");
        return true;
    }
    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     *
     * @param map API返回的XML数据字符串对应 map
     * @return API签名是否合法
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static boolean checkIsSignValidFromMap(Map<String, String> map, boolean isGZH) throws ParserConfigurationException, IOException, SAXException, IllegalAccessException {
        logger.info(map.toString());
        String signFromAPIResponse = map.get("sign").toString();
        if (StringUtils.isBlank(signFromAPIResponse)) {
            logger.info("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign", "");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = Signature.getSign(map, isGZH);
        if (StringUtils.equals(signForAPIResponse, signFromAPIResponse)) {
            //签名验不过，表示这个API返回的数据有可能已经被篡改了
            logger.info("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
            return false;
        }
        logger.info("恭喜，API返回的数据签名验证通过!!!");
        return true;
    }
}
