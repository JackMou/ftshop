/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: tJRpPVKIcuZaedeacNWZP0brJL8lYRkJ
 */
package com.futengwl.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futengwl.Pageable;
import com.futengwl.Results;
import com.futengwl.entity.Admin;
import com.futengwl.entity.Business;
import com.futengwl.entity.BusinessDepositLog;
import com.futengwl.security.CurrentUser;
import com.futengwl.service.BusinessDepositLogService;
import com.futengwl.service.BusinessService;

/**
 * Controller - 商家预存款
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("adminBusinessDepositController")
@RequestMapping("/admin/business_deposit")
public class BusinessDepositController extends BaseController {

	@Inject
	private BusinessDepositLogService businessDepositLogService;
	@Inject
	private BusinessService businessService;

	/**
	 * 商家选择
	 */
	@GetMapping("/business_select")
	public ResponseEntity<?> businessSelect(String keyword) {
		List<Map<String, Object>> data = new ArrayList<>();
		if (StringUtils.isEmpty(keyword)) {
			return ResponseEntity.ok(data);
		}
		List<Business> businesses = businessService.search(keyword, null);
		for (Business business : businesses) {
			Map<String, Object> item = new HashMap<>();
			item.put("id", business.getId());
			item.put("name", business.getUsername());
			item.put("availableBalance", business.getAvailableBalance());
			data.add(item);
		}
		return ResponseEntity.ok(data);
	}

	/**
	 * 调整
	 */
	@GetMapping("/adjust")
	public String adjust() {
		return "admin/business_deposit/adjust";
	}

	/**
	 * 调整
	 */
	@PostMapping("/adjust")
	public ResponseEntity<?> adjust(Long businessId, BigDecimal amount, String memo, @CurrentUser Admin currentUser) {
		Business business = businessService.find(businessId);
		if (business == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (business.getBalance() == null || business.getAvailableBalance().add(amount).compareTo(BigDecimal.ZERO) < 0) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		businessService.addBalance(business, amount, BusinessDepositLog.Type.ADJUSTMENT, memo);
		return Results.OK;
	}

	/**
	 * 记录
	 */
	@GetMapping("/log")
	public String log(Long businessId, Pageable pageable, ModelMap model) {
		Business business = businessService.find(businessId);
		if (business != null) {
			model.addAttribute("business", business);
			model.addAttribute("page", businessDepositLogService.findPage(business, pageable));
		} else {
			model.addAttribute("page", businessDepositLogService.findPage(pageable));
		}
		return "admin/business_deposit/log";
	}

}