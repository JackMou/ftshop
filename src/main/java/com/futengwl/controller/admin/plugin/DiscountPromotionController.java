/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: haZRYZHYG8L18581V4M4gJspKvSBZWFi
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
import com.futengwl.entity.Promotion;
import com.futengwl.plugin.DiscountPromotionPlugin;
import com.futengwl.plugin.PromotionPlugin;
import com.futengwl.service.PluginConfigService;
import com.futengwl.service.PromotionService;

/**
 * Controller - 折扣促销
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("adminPluginDiscountPromotionController")
@RequestMapping("/admin/plugin/discount_promotion")
public class DiscountPromotionController extends BaseController {

	@Inject
	private DiscountPromotionPlugin discountPromotionPlugin;
	@Inject
	private PluginConfigService pluginConfigService;
	@Inject
	private PromotionService promotionService;

	/**
	 * 安装
	 */
	@PostMapping("/install")
	public ResponseEntity<?> install() {
		if (!discountPromotionPlugin.getIsInstalled()) {
			PluginConfig pluginConfig = new PluginConfig();
			pluginConfig.setPluginId(discountPromotionPlugin.getId());
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
		if (discountPromotionPlugin.getIsInstalled()) {
			pluginConfigService.deleteByPluginId(discountPromotionPlugin.getId());
			promotionService.shutDownPromotion(Promotion.Type.DISCOUNT);
		}
		return Results.OK;
	}

	/**
	 * 设置
	 */
	@GetMapping("/setting")
	public String setting(ModelMap model) {
		PluginConfig pluginConfig = discountPromotionPlugin.getPluginConfig();
		model.addAttribute("pluginConfig", pluginConfig);
		return "/admin/plugin/discount_promotion/setting";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(BigDecimal price, @RequestParam(defaultValue = "false") Boolean isEnabled, Integer order) {
		if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		PluginConfig pluginConfig = discountPromotionPlugin.getPluginConfig();
		Map<String, String> attributes = new HashMap<>();
		pluginConfig.setAttributes(attributes);
		attributes.put(PromotionPlugin.PRICE, String.valueOf(price));
		pluginConfig.setIsEnabled(isEnabled);
		pluginConfig.setOrder(order);
		pluginConfigService.update(pluginConfig);
		if (!pluginConfig.getIsEnabled()) {
			promotionService.shutDownPromotion(Promotion.Type.DISCOUNT);
		}
		return Results.OK;
	}

}