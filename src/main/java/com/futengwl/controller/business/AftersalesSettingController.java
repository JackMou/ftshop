/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: dX86K8fXQsxuUhrpc/iumvXxzEbsScBQ
 */
package com.futengwl.controller.business;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futengwl.Results;
import com.futengwl.entity.AftersalesSetting;
import com.futengwl.entity.Store;
import com.futengwl.security.CurrentStore;
import com.futengwl.service.AftersalesSettingService;

/**
 * Controller - 售后设置
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("businessAftersalesSettingController")
@RequestMapping("business/aftersales_setting")
public class AftersalesSettingController extends BaseController {

	@Inject
	private AftersalesSettingService aftersalesSettingService;

	/**
	 * 查看
	 */
	@GetMapping("/view")
	public String view(@CurrentStore Store currentStore, ModelMap model) {
		AftersalesSetting aftersalesSetting = aftersalesSettingService.findByStore(currentStore);
		if (aftersalesSetting == null) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}

		model.addAttribute("aftersalesSetting", aftersalesSetting);
		return "business/aftersales_setting/view";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(AftersalesSetting aftersalesSettingForm, @CurrentStore Store currentStore) {
		AftersalesSetting aftersalesSetting = aftersalesSettingService.findByStore(currentStore);
		if (aftersalesSetting == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}

		BeanUtils.copyProperties(aftersalesSettingForm, aftersalesSetting, "id", "store");
		aftersalesSettingService.update(aftersalesSetting);
		return Results.OK;
	}

}