/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: bVChqemtcDKEGK/Vzd44j3UZtRDUdY7P
 */
package com.futengwl.controller.member;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.futengwl.Results;
import com.futengwl.entity.Member;
import com.futengwl.security.CurrentUser;
import com.futengwl.service.MemberService;

/**
 * Controller - 密码
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("memberPasswordController")
@RequestMapping("/member/password")
public class PasswordController extends BaseController {

	@Inject
	private MemberService memberService;

	/**
	 * 验证当前密码
	 */
	@GetMapping("/check_current_password")
	public @ResponseBody boolean checkCurrentPassword(String currentPassword, @CurrentUser Member currentUser) {
		return StringUtils.isNotEmpty(currentPassword) && currentUser.isValidCredentials(currentPassword);
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit() {
		return "member/password/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(String currentPassword, String password, @CurrentUser Member currentUser) {
		if (StringUtils.isEmpty(password) || StringUtils.isEmpty(currentPassword)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (!isValid(Member.class, "password", password)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (!currentUser.isValidCredentials(currentPassword)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		currentUser.setPassword(password);
		memberService.update(currentUser);
		return Results.OK;
	}

}