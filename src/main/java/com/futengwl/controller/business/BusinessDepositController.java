/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: TebsLGi1fS4TVhjh5HxoetZ+cn8K2q87
 */
package com.futengwl.controller.business;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.futengwl.Pageable;
import com.futengwl.Results;
import com.futengwl.entity.Business;
import com.futengwl.plugin.PaymentPlugin;
import com.futengwl.security.CurrentUser;
import com.futengwl.service.BusinessDepositLogService;
import com.futengwl.service.PluginService;
import com.futengwl.util.WebUtils;

/**
 * Controller - 预存款
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("businessDepositController")
@RequestMapping("/business/business_deposit")
public class BusinessDepositController extends BaseController {

	@Inject
	private BusinessDepositLogService businessDepositLogService;
	@Inject
	private PluginService pluginService;

	/**
	 * 计算
	 */
	@GetMapping("/calculate")
	public ResponseEntity<?> calculate(String paymentPluginId, BigDecimal rechargeAmount) {
		Map<String, Object> data = new HashMap<>();
		PaymentPlugin paymentPlugin = pluginService.getPaymentPlugin(paymentPluginId);
		if (paymentPlugin == null || !paymentPlugin.getIsEnabled()) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (rechargeAmount == null || rechargeAmount.compareTo(BigDecimal.ZERO) <= 0) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		data.put("fee", paymentPlugin.calculateFee(rechargeAmount));
		return ResponseEntity.ok(data);
	}

	/**
	 * 检查余额
	 */
	@PostMapping("/check_balance")
	public @ResponseBody Map<String, Object> checkBalance(@CurrentUser Business currenUser) {
		Map<String, Object> data = new HashMap<>();
		data.put("balance", currenUser.getBalance());
		return data;
	}

	/**
	 * 充值
	 */
	@GetMapping("/recharge")
	public String recharge(ModelMap model) {
		List<PaymentPlugin> paymentPlugins = pluginService.getActivePaymentPlugins(WebUtils.getRequest());
		if (!paymentPlugins.isEmpty()) {
			model.addAttribute("defaultPaymentPlugin", paymentPlugins.get(0));
			model.addAttribute("paymentPlugins", paymentPlugins);
		}
		return "business/business_deposit/recharge";
	}

	/**
	 * 记录
	 */
	@GetMapping("/log")
	public String log(Pageable pageable, @CurrentUser Business currenUser, ModelMap model) {
		model.addAttribute("page", businessDepositLogService.findPage(currenUser, pageable));
		return "business/business_deposit/log";
	}

}