/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: vskmnmhRTFpJk2RGUP2aQXloOQLIQK3z
 */
package com.futengwl.controller.admin;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.futengwl.Results;
import com.futengwl.TemplateConfig;
import com.futengwl.service.TemplateService;
import com.futengwl.util.SystemUtils;

/**
 * Controller - 模板
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("adminTemplateController")
@RequestMapping("/admin/template")
public class TemplateController extends BaseController {

	@Inject
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Inject
	private TemplateService templateService;

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(String id, ModelMap model) {
		model.addAttribute("templateConfig", SystemUtils.getTemplateConfig(id));
		model.addAttribute("content", templateService.read(id));
		return "admin/template/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(String id, String content) {
		if (StringUtils.isEmpty(id) || content == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		templateService.write(id, content);
		freeMarkerConfigurer.getConfiguration().clearTemplateCache();
		return Results.OK;
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(TemplateConfig.Type type, ModelMap model) {
		model.addAttribute("type", type);
		model.addAttribute("types", TemplateConfig.Type.values());
		model.addAttribute("templateConfigs", SystemUtils.getTemplateConfigs(type));
		return "admin/template/list";
	}

}