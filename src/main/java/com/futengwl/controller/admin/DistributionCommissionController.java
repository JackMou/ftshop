/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: SO2r60eqb1gSsGIxsEUTAa2JI/sG0cCm
 */
package com.futengwl.controller.admin;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futengwl.Pageable;
import com.futengwl.service.DistributionCommissionService;

/**
 * Controller - 分销佣金
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("adminDistributionCommissionController")
@RequestMapping("/admin/distribution_commission")
public class DistributionCommissionController extends BaseController {

	@Inject
	private DistributionCommissionService distributionCommissionService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", distributionCommissionService.findPage(pageable));
		return "admin/distribution_commission/list";
	}

}