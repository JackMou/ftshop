/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: oj0sfArAE5UGoKoyOyVC7E1ATNPGBxjf
 */
package com.futengwl.controller.member;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.futengwl.service.AuthCodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futengwl.entity.Member;
import com.futengwl.entity.SocialUser;
import com.futengwl.security.CurrentUser;
import com.futengwl.service.PluginService;
import com.futengwl.service.SocialUserService;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 会员登录
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("memberLoginController")
@RequestMapping("/member/login")
public class LoginController extends BaseController {

	@Value("${security.member_login_success_url}")
	private String memberLoginSuccessUrl;

	@Inject
	private PluginService pluginService;
	@Inject
	private SocialUserService socialUserService;
	@Inject
	private AuthCodeService authCodeService;

	/**
	 * 登录页面
	 */
	@GetMapping
	public String index(Long socialUserId, String uniqueId, @CurrentUser Member currentUser, HttpServletRequest request, ModelMap model) {
		if (socialUserId != null && StringUtils.isNotEmpty(uniqueId)) {
			SocialUser socialUser = socialUserService.find(socialUserId);
			if (socialUser == null || socialUser.getUser() != null || !StringUtils.equals(socialUser.getUniqueId(), uniqueId)) {
				return UNPROCESSABLE_ENTITY_VIEW;
			}
			model.addAttribute("socialUserId", socialUserId);
			model.addAttribute("uniqueId", uniqueId);
		}
		model.addAttribute("memberLoginSuccessUrl", memberLoginSuccessUrl);
		model.addAttribute("loginPlugins", pluginService.getActiveLoginPlugins(request));
		return "/member/login/index";
	}

	/**
	 * 获取注册验证码
	 */
	@GetMapping("/authCode")
	public @ResponseBody
	String
	sendAuthCode(String username) {
		if(authCodeService.sendAuthCode(username,"login")){
			return "success";
		}
		return "failure";
	}

}