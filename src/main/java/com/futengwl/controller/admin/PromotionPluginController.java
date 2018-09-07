/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: zZvfilQ50+jPds6te3FXT8eGR5DSqk6A
 */
package com.futengwl.controller.admin;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futengwl.service.PluginService;

/**
 * Controller - 促销插件
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("adminPromotionPluginController")
@RequestMapping("/admin/promotion_plugin")
public class PromotionPluginController extends BaseController {

	@Inject
	private PluginService pluginService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(ModelMap model) {
		model.addAttribute("promotionPlugins", pluginService.getPromotionPlugins());
		return "admin/promotion_plugin/list";
	}

}