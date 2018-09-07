/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: M2YLb+hJOU7svaZJCONyg+vODo2Ky8dr
 */
package com.futengwl.controller.business;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.futengwl.Results;
import com.futengwl.Setting;
import com.futengwl.entity.BaseEntity;
import com.futengwl.entity.Business;
import com.futengwl.entity.BusinessAttribute;
import com.futengwl.security.UserAuthenticationToken;
import com.futengwl.service.BusinessAttributeService;
import com.futengwl.service.BusinessService;
import com.futengwl.service.UserService;
import com.futengwl.util.SystemUtils;

/**
 * Controller - 商家注册
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("businessRegisterController")
@RequestMapping("/business/register")
public class RegisterController extends BaseController {

	@Inject
	private UserService userService;
	@Inject
	private BusinessService businessService;
	@Inject
	private BusinessAttributeService businessAttributeService;

	/**
	 * 检查用户名是否存在
	 */
	@GetMapping("/check_username")
	public @ResponseBody boolean checkUsername(String username) {
		return StringUtils.isNotEmpty(username) && !businessService.usernameExists(username);
	}

	/**
	 * 检查E-mail是否存在
	 */
	@GetMapping("/check_email")
	public @ResponseBody boolean checkEmail(String email) {
		return StringUtils.isNotEmpty(email) && !businessService.emailExists(email);
	}

	/**
	 * 检查手机是否存在
	 */
	@GetMapping("/check_mobile")
	public @ResponseBody boolean checkMobile(String mobile) {
		return StringUtils.isNotEmpty(mobile) && !businessService.mobileExists(mobile);
	}

	/**
	 * 注册页面
	 */
	@GetMapping
	public String index(ModelMap model) {
		return "business/register/index";
	}

	/**
	 * 注册提交
	 */
	@PostMapping("/submit")
	public ResponseEntity<?> submit(String username, String password, String email, String mobile, HttpServletRequest request) throws IOException {
		Setting setting = SystemUtils.getSetting();
		if (!ArrayUtils.contains(setting.getAllowedRegisterTypes(), Setting.RegisterType.BUSINESS)) {
			return Results.unprocessableEntity("business.register.disabled");
		}
		if (!isValid(Business.class, "username", username, BaseEntity.Save.class) || !isValid(Business.class, "password", password, BaseEntity.Save.class) || !isValid(Business.class, "email", email, BaseEntity.Save.class) || !isValid(Business.class, "mobile", mobile, BaseEntity.Save.class)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (businessService.usernameExists(username)) {
			return Results.unprocessableEntity("business.register.usernameExist");
		}
		if (businessService.emailExists(email)) {
			return Results.unprocessableEntity("business.register.emailExist");
		}
		if (businessService.mobileExists(mobile)) {
			return Results.unprocessableEntity("business.register.mobileExist");
		}

		Business business = new Business();
		business.removeAttributeValue();
		for (BusinessAttribute businessAttribute : businessAttributeService.findList(true, true)) {
			String[] values = request.getParameterValues("businessAttribute_" + businessAttribute.getId());
			if (!businessAttributeService.isValid(businessAttribute, values)) {
				return Results.UNPROCESSABLE_ENTITY;
			}
			Object memberAttributeValue = businessAttributeService.toBusinessAttributeValue(businessAttribute, values);
			business.setAttributeValue(businessAttribute, memberAttributeValue);
		}

		business.setUsername(username);
		business.setPassword(password);
		business.setEmail(email);
		business.setMobile(mobile);
		business.setBalance(BigDecimal.ZERO);
		business.setFrozenAmount(BigDecimal.ZERO);
		business.setStore(null);
		business.setBusinessCashs(null);
		business.setBusinessDepositLogs(null);
		business.setIsEnabled(true);
		business.setIsLocked(false);
		business.setLockDate(null);
		business.setLastLoginIp(request.getRemoteAddr());
		business.setLastLoginDate(new Date());

		userService.register(business,"");
		userService.login(new UserAuthenticationToken(Business.class, username, password, false, request.getRemoteAddr()));
		return Results.OK;
	}

}