/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 0N6XAZbHtBWO7gMzCkwwiawA4u2spKMD
 */
package com.futengwl.controller.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futengwl.entity.Admin;
import com.futengwl.security.CurrentUser;

/**
 * Controller - 管理员登录
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("adminLoginController")
@RequestMapping("/admin")
public class LoginController extends BaseController {

	@Value("${security.admin_login_success_url}")
	private String adminLoginSuccessUrl;

	/**
	 * 登录跳转
	 */
	@GetMapping({ "", "/" })
	public String index() {
		return "redirect:/admin/login";
	}

	/**
	 * 登录页面
	 */
	@GetMapping("/login")
	public String login(@CurrentUser Admin currentUser, ModelMap model) {
		if (currentUser != null) {
			return "redirect:" + adminLoginSuccessUrl;
		}
		model.addAttribute("adminLoginSuccessUrl", adminLoginSuccessUrl);
		return "admin/login/index";
	}

}