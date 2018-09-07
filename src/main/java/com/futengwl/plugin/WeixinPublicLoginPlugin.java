/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 0BE9+6iYKnR66SE7QnlcF/+/eFCZmU44
 */
package com.futengwl.plugin;

import com.futengwl.util.JsonUtils;
import com.futengwl.util.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Plugin - 微信登录(公众号登录)
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Component("weixinPublicLoginPlugin")
public class WeixinPublicLoginPlugin extends LoginPlugin {

	/**
	 * code请求URL
	 */
	private static final String CODE_REQUEST_URL = "https://open.weixin.qq.com/connect/oauth2/authorize#wechat_redirect";

	/**
	 * openId请求URL
	 */
	private static final String OPEN_ID_REQUEST_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

	@Override
	public String getName() {
		return "微信登录(公众号登录)";
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
		return "/admin/plugin/weixin_public_login/install";
	}

	@Override
	public String getUninstallUrl() {
		return "/admin/plugin/weixin_public_login/uninstall";
	}

	@Override
	public String getSettingUrl() {
		return "/admin/plugin/weixin_public_login/setting";
	}

	@Override
	public boolean supports(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT");
		if(StringUtils.containsIgnoreCase(userAgent, "miniprogram")){
			return false;
		}
		return StringUtils.containsIgnoreCase(userAgent, "micromessenger");
	}

	@Override
	public void signInHandle(LoginPlugin loginPlugin, String extra, HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception {
		Map<String, Object> parameterMap = new TreeMap<>();
		parameterMap.put("appid", getAppId());
		parameterMap.put("redirect_uri", getPostSignInUrl(loginPlugin));
		parameterMap.put("response_type", "code");
		parameterMap.put("scope", "snsapi_userinfo");

		modelAndView.addObject("requestUrl", CODE_REQUEST_URL);
		modelAndView.addObject("parameterMap", parameterMap);
		modelAndView.setViewName(LoginPlugin.DEFAULT_SIGN_IN_VIEW_NAME);
	}

	@Override
	public boolean isSignInSuccess(LoginPlugin loginPlugin, String extra, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		if (StringUtils.isEmpty(code)) {
			return false;
		}

		Map params = new HashMap<String, String>();
        params.put("appid", getAppId());
        params.put("secret", getAppSecret());
        params.put("code",code);
		params.put("grant_type", "authorization_code");

		String result = WebUtils.get(OPEN_ID_REQUEST_URL, params);
		Map resultMap = JsonUtils.toObject(result, Map.class);
		System.out.println(result);
		String openId=String.valueOf(resultMap.get("openid"));
		if (StringUtils.isNotEmpty(openId) ) {
            request.setAttribute("openid", openId);
			return true;
		}
		return false;
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

	/**
	 * 微信公众号分享
	 *
	 * @return AppSecret
	 */
	private String share() {
		return getAttribute("appSecret");
	}

}