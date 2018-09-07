/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: RcM+qtW2md4hA6hlJCyM+upAgyeaT3Hf
 */
package com.futengwl.controller.admin;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futengwl.service.PluginService;

/**
 * Controller - 支付插件
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("adminPaymentPluginController")
@RequestMapping("/admin/payment_plugin")
public class PaymentPluginController extends BaseController {

	@Inject
	private PluginService pluginService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(ModelMap model) {
		model.addAttribute("paymentPlugins", pluginService.getPaymentPlugins());
		return "admin/payment_plugin/list";
	}

}