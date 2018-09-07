/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: aoPq8Ga+rO7y3tgNQ5XMp/+Mp6wvS9AD
 */
package com.futengwl.controller.member;

import javax.inject.Inject;

import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.entity.Area;
import com.futengwl.util.JsonUtils;
import com.futengwl.util.WebUtils;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futengwl.Pageable;
import com.futengwl.Results;
import com.futengwl.entity.Member;
import com.futengwl.entity.Receiver;
import com.futengwl.exception.UnauthorizedException;
import com.futengwl.security.CurrentUser;
import com.futengwl.service.AreaService;
import com.futengwl.service.ReceiverService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Controller - 收货地址
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("memberReceiverController")
@RequestMapping("/member/receiver")
public class ReceiverController extends BaseController {

	/**
	 * 每页记录数
	 */
	private static final int PAGE_SIZE = 10;

	@Inject
	private AreaService areaService;
	@Inject
	private ReceiverService receiverService;

	/**
	 * 添加属性
	 */
	@ModelAttribute
	public void populateModel(Long receiverId, @CurrentUser Member currentUser, ModelMap model) {
		Receiver receiver = receiverService.find(receiverId);
		if (receiver != null && !currentUser.equals(receiver.getMember())) {
			throw new UnauthorizedException();
		}
		model.addAttribute("receiver", receiver);
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Integer pageNumber, @CurrentUser Member currentUser, ModelMap model) {
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("page", receiverService.findPage(currentUser, pageable));
		return "member/receiver/list";
	}

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(String location,ModelMap model) {
		String areaName=areaService.getAreaNameByGPSLocation(location);
		if(StringUtils.isNotEmpty(areaName)){
            Area area = areaService.findOne(areaName);
            if(area!=null){
                model.addAttribute("areaId",area.getId());
                model.addAttribute("areaTreePath",area.getTreePath());
            }
        }

		return "member/receiver/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public ResponseEntity<?> save(@ModelAttribute("receiverForm") Receiver receiverForm, Long areaId, @CurrentUser Member currentUser) {
		receiverForm.setArea(areaService.find(areaId));
		if (!isValid(receiverForm)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (Receiver.MAX_RECEIVER_COUNT != null && currentUser.getReceivers().size() >= Receiver.MAX_RECEIVER_COUNT) {
			return Results.unprocessableEntity("member.receiver.addCountNotAllowed", Receiver.MAX_RECEIVER_COUNT);
		}
		receiverForm.setAreaName(null);
		receiverForm.setMember(currentUser);
		receiverService.save(receiverForm);
		return Results.OK;
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(@ModelAttribute(binding = false) Receiver receiver, ModelMap model) {
		if (receiver == null) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}

		model.addAttribute("receiver", receiver);
		return "member/receiver/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(@ModelAttribute("receiverForm") Receiver receiverForm, @ModelAttribute(binding = false) Receiver receiver, Long areaId) {
		if (receiver == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}

		receiverForm.setArea(areaService.find(areaId));
		if (!isValid(receiverForm)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		BeanUtils.copyProperties(receiverForm, receiver, "id", "areaName", "member");
		receiverService.update(receiver);
		return Results.OK;
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@ModelAttribute(binding = false) Receiver receiver) {
		if (receiver == null) {
			return Results.NOT_FOUND;
		}

		receiverService.delete(receiver);
		return Results.OK;
	}

}