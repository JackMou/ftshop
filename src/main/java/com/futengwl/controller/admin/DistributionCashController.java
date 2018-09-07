/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: xEEdtRn/M0krdfe99MXlp9tZ3+NqYosV
 */
package com.futengwl.controller.admin;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futengwl.Pageable;
import com.futengwl.Results;
import com.futengwl.entity.DistributionCash;
import com.futengwl.service.DistributionCashService;

/**
 * Controller - 分销提现
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("adminDistributionCashController")
@RequestMapping("/admin/distribution_cash")
public class DistributionCashController extends BaseController {

	@Inject
	private DistributionCashService distributionCashService;

	/**
	 * 审核
	 */
	@PostMapping("/review")
	public ResponseEntity<?> review(Long id, Boolean isPassed) {
		DistributionCash distributionCash = distributionCashService.find(id);
		if (isPassed == null || distributionCash == null || !DistributionCash.Status.PENDING.equals(distributionCash.getStatus())) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		distributionCashService.review(distributionCash, isPassed);
		return Results.OK;
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(DistributionCash.Status status, Pageable pageable, ModelMap model) {
		model.addAttribute("status", status);
		model.addAttribute("page", distributionCashService.findPage(status, null, null, null, null, pageable));
		return "admin/distribution_cash/list";
	}

}