package com.futengwl.controller.shop;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.futengwl.entity.Member;
import com.futengwl.entity.Order.Status;
import com.futengwl.entity.Order.Type;
import com.futengwl.plugin.WeixinPublicLoginPlugin;
import com.futengwl.security.CurrentUser;
import com.futengwl.service.OrderService;
import com.futengwl.util.JsonUtils;
import com.futengwl.util.WebUtils;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

@Controller
@RequestMapping("/shop/share")
public class ShareController {

    @Inject
    private CacheManager cacheManager;
    @Inject
    private WeixinPublicLoginPlugin loginPlugin;
    @Inject
    private OrderService orderService;

    private final String CACHE_ID_ACCESS_TOKEN = "ACCESS_TOKEN";
    private final String CACHE_ID_TICKET = "TICKET";

    private final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    private final String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    
    
    @GetMapping("/weixin")
    private @ResponseBody
    Map weixinShare(@CurrentUser Member currentUser,String url, String productId) {    	
    	Long count = orderService.count(Type.GENERAL, Status.COMPLETED, null, currentUser.getId(), Long.valueOf(productId), null, null, null, null, null, false);
        if (count == 0) {
        	Map map = new HashMap<>();
        	map.put("status", "1");
        	map.put("msg", "当前用户没有购买过此商品,分享后无奖励哦!");
			return map;
		} else {
			Map map = getSign(getTicket(getAccessToken()), url);
			if(null!=currentUser) {
				map.put("currentUserId", currentUser.getId());
			}
			return map;
		}
    }

    //1. 获取access_token,存入缓存
    //https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
    private String getAccessToken() {
        String accessToken = (String) getCache(CACHE_ID_ACCESS_TOKEN);
        if (null != accessToken && !((String) getCache(CACHE_ID_ACCESS_TOKEN)).isEmpty()) {
            return (String) getCache(CACHE_ID_ACCESS_TOKEN);
        }
        Map params = new HashMap<String, String>();
        params.put("grant_type", "client_credential");
        params.put("appid", loginPlugin.getPluginConfig().getAttribute("appId"));
        params.put("secret", loginPlugin.getPluginConfig().getAttribute("appSecret"));
        String result = WebUtils.get(ACCESS_TOKEN_URL, params);
        Map resultMap = JsonUtils.toObject(result, Map.class);
        System.out.println(result);
        if (null != resultMap.get("access_token")) {
            //写入缓存
            updateCache(CACHE_ID_ACCESS_TOKEN, (String) resultMap.get("access_token"));
            return (String) resultMap.get("access_token");
        }
        return null;
    }


    //2. 通过access_token获取ticket
    //https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
    private String getTicket(String accessToken) {
        if (null != (String) getCache(CACHE_ID_TICKET) && !((String) getCache(CACHE_ID_TICKET)).isEmpty()) {
            return (String) getCache(CACHE_ID_TICKET);
        }
        Map params = new HashMap<String, String>();
        params.put("access_token", accessToken);
        params.put("type", "jsapi");
        String result = WebUtils.get(TICKET_URL, params);
        System.out.println(result);
        Map resultMap = JsonUtils.toObject(result, Map.class);
        if ("ok".equals(resultMap.get("errmsg"))) {
            updateCache(CACHE_ID_TICKET, (String) resultMap.get("ticket"));
            return (String) resultMap.get("ticket");
        }
        return null;
    }

    //3. 生成签名，并返回
    private Map getSign(String ticket, String url) {
        try {
            String noncestr = DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30));
            Map<String, Object> params = new TreeMap<>();
            params.put("jsapi_ticket", ticket);
            params.put("noncestr", noncestr);
            params.put("timestamp", String.valueOf(new Date().getTime()/1000));
            params.put("url", URLDecoder.decode(url, "UTF-8"));
            params.put("signature", generateSign(params));
            params.remove("jsapi_ticket");
            params.put("appId", loginPlugin.getPluginConfig().getAttribute("appId"));
            return params;
        } catch (Exception ex) {

        }
        return null;
    }

    /**
     * 生成签名
     *
     * @param parameterMap 参数
     * @return 签名
     */
    private String generateSign(Map<String, ?> parameterMap) {
        return String.valueOf(DigestUtils.sha1Hex(joinKeyValue(new TreeMap<>(parameterMap), null, null, "&", true)));
    }

    /**
     * 连接Map键值对
     *
     * @param map              Map
     * @param prefix           前缀
     * @param suffix           后缀
     * @param separator        连接符
     * @param ignoreEmptyValue 忽略空值
     * @param ignoreKeys       忽略Key
     * @return 字符串
     */
    protected String joinKeyValue(Map<String, Object> map, String prefix, String suffix, String separator, boolean ignoreEmptyValue, String... ignoreKeys) {
        List<String> list = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = ConvertUtils.convert(entry.getValue());
                if (StringUtils.isNotEmpty(key) && !ArrayUtils.contains(ignoreKeys, key) && (!ignoreEmptyValue || StringUtils.isNotEmpty(value))) {
                    list.add(key + "=" + (value != null ? value : StringUtils.EMPTY));
                }
            }
        }
        return (prefix != null ? prefix : StringUtils.EMPTY) + StringUtils.join(list, separator) + (suffix != null ? suffix : StringUtils.EMPTY);
    }

    private Object getCache(String id) {
        Ehcache cache = cacheManager.getEhcache("weixinShare");
        try {
            Element element = cache.get(id);
            if (element != null) {
                return element.getObjectValue();
            }
        } finally {

        }
        return null;
    }


    private void updateCache(String id, String value) {
        Ehcache cache = cacheManager.getEhcache("weixinShare");
        cache.acquireWriteLockOnKey(id);
        try {
            cache.put(new Element(id, value));
        } finally {
            cache.releaseWriteLockOnKey(id);
        }
    }
}
