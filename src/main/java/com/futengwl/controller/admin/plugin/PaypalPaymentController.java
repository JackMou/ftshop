/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 9ZNOfTBAJA3LKpXLug2jKCf1i8X/zNCK
 */
package com.futengwl.controller.admin.plugin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.futengwl.Results;
import com.futengwl.controller.admin.BaseController;
import com.futengwl.entity.PluginConfig;
import com.futengwl.plugin.PaymentPlugin;
import com.futengwl.plugin.PaypalPaymentPlugin;
import com.futengwl.service.PluginConfigService;

/**
 * Controller - Paypal
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("adminPluginPaypalPaymentController")
@RequestMapping("/admin/plugin/paypal_payment")
public class PaypalPaymentController extends BaseController {

	@Inject
	private PaypalPaymentPlugin paypalPaymentPlugin;
	@Inject
	private PluginConfigService pluginConfigService;

	/**
	 * 安装
	 */
	@PostMapping("/install")
	public ResponseEntity<?> install() {
		if (!paypalPaymentPlugin.getIsInstalled()) {
			PluginConfig pluginConfig = new PluginConfig();
			pluginConfig.setPluginId(paypalPaymentPlugin.getId());
			pluginConfig.setIsEnabled(false);
			pluginConfig.setAttributes(null);
			pluginConfigService.save(pluginConfig);
		}
		return Results.OK;
	}

	/**
	 * 卸载
	 */
	@PostMapping("/uninstall")
	public ResponseEntity<?> uninstall() {
		if (paypalPaymentPlugin.getIsInstalled()) {
			pluginConfigService.deleteByPluginId(paypalPaymentPlugin.getId());
		}
		return Results.OK;
	}

	/**
	 * 设置
	 */
	@GetMapping("/setting")
	public String setting(ModelMap model) {
		PluginConfig pluginConfig = paypalPaymentPlugin.getPluginConfig();
		model.addAttribute("currencies", PaypalPaymentPlugin.Currency.values());
		model.addAttribute("feeTypes", PaymentPlugin.FeeType.values());
		model.addAttribute("pluginConfig", pluginConfig);
		return "/admin/plugin/paypal_payment/setting";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(String displayName, String partner, PaypalPaymentPlugin.Currency currency, PaymentPlugin.FeeType feeType, BigDecimal fee, String logo, String description, @RequestParam(defaultValue = "false") Boolean isEnabled, Integer order) {
		PluginConfig pluginConfig = paypalPaymentPlugin.getPluginConfig();
		Map<String, String> attributes = new HashMap<>();
		attributes.put(PaymentPlugin.DISPLAY_NAME_ATTRIBUTE_NAME, displayName);
		attributes.put("partner", partner);
		attributes.put("currency", String.valueOf(currency));
		attributes.put(PaymentPlugin.FEE_TYPE_ATTRIBUTE_NAME, String.valueOf(feeType));
		attributes.put(PaymentPlugin.FEE_ATTRIBUTE_NAME, String.valueOf(fee));
		attributes.put(PaymentPlugin.LOGO_ATTRIBUTE_NAME, logo);
		attributes.put(PaymentPlugin.DESCRIPTION_ATTRIBUTE_NAME, description);
		pluginConfig.setAttributes(attributes);
		pluginConfig.setIsEnabled(isEnabled);
		pluginConfig.setOrder(order);
		pluginConfigService.update(pluginConfig);
		return Results.OK;
	}

}