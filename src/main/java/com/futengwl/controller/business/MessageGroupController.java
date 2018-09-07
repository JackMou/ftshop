/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: kzykAwV6gpx0QoLJEoK9HYBYRfMsi8ud
 */
package com.futengwl.controller.business;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonView;

import com.futengwl.Pageable;
import com.futengwl.Results;
import com.futengwl.entity.BaseEntity;
import com.futengwl.entity.Business;
import com.futengwl.entity.MessageGroup;
import com.futengwl.exception.UnauthorizedException;
import com.futengwl.security.CurrentUser;
import com.futengwl.service.MessageGroupService;

/**
 * Controller - 商家中心 - 消息组
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("businessMessageGroupController")
@RequestMapping("/business/message_group")
public class MessageGroupController extends BaseController {

	@Inject
	private MessageGroupService messageGroupService;

	/**
	 * 添加属性
	 */
	@ModelAttribute
	public void populateModel(Long messageGroupId, @CurrentUser Business currentUser, ModelMap model) {
		MessageGroup messageGroup = messageGroupService.find(messageGroupId);
		if (messageGroup != null && !currentUser.equals(messageGroup.getUser1()) && !currentUser.equals(messageGroup.getUser2())) {
			throw new UnauthorizedException();
		}
		model.addAttribute("messageGroup", messageGroup);
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Integer pageNumber, @CurrentUser Business currentUser, Pageable pageable, Model model) {
		model.addAttribute("page", messageGroupService.findPage(currentUser, pageable));
		return "business/message_group/list";
	}

	/**
	 * 列表
	 */
	@GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(BaseEntity.BaseView.class)
	public ResponseEntity<?> list(Integer pageNumber, Pageable pageable, @CurrentUser Business currentUser) {
		return ResponseEntity.ok(messageGroupService.findPage(currentUser, pageable).getContent());
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@ModelAttribute(binding = false) MessageGroup messageGroup, @CurrentUser Business currentUser) {
		if (messageGroup == null) {
			return Results.NOT_FOUND;
		}

		messageGroupService.delete(messageGroup, currentUser);
		return Results.OK;
	}

}