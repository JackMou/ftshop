/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: Mb7P0Yi2pvHEcg14eztkIAlevt1KEb66
 */
package com.futengwl.controller.shop;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonView;
import com.futengwl.Pageable;
import com.futengwl.Results;
import com.futengwl.entity.BaseEntity;
import com.futengwl.entity.Store;
import com.futengwl.exception.ResourceNotFoundException;
import com.futengwl.service.StoreService;

/**
 * Controller - 店铺
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("shopStoreController")
@RequestMapping("/store")
public class StoreController extends BaseController {

	@Inject
	private StoreService storeService;

	/**
	 * 每页记录数
	 */
	private static final int PAGE_SIZE = 20;
	
	
	@GetMapping("/")
	public String index() {
		return "shop/index";
	}

	/**
	 * 首页
	 */
	@GetMapping("/{storeId}")
	public String index(@PathVariable Long storeId, ModelMap model, HttpServletResponse response) {		
		Store store = storeService.find(storeId);
		if (store == null) {
			throw new ResourceNotFoundException();
		}
		model.addAttribute("store", store);
		//大连代理商店铺
//		if(store.getId()==10001){
//			model.addAttribute("homeURL","/store/"+store.getId());
//		}else{
//			model.addAttribute("homeURL","/");
//		}

		model.addAttribute("homeURL","/store/"+store.getId());
		return "shop/store/index";
	}

	/**
	 * 搜索
	 */
	@GetMapping("/search")
	public String search(String keyword, Integer pageNumber, Integer pageSize, ModelMap model) {
		if (StringUtils.isEmpty(keyword)) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}

		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("storeKeyword", keyword);
		model.addAttribute("searchType", "STORE");
		model.addAttribute("page", storeService.search(keyword, pageable));
		return "shop/store/search";
	}

	/**
	 * 搜索
	 */
	@GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(BaseEntity.BaseView.class)
	public ResponseEntity<?> search(String keyword, Integer pageNumber) {
		if (StringUtils.isEmpty(keyword)) {
			return Results.NOT_FOUND;
		}

		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		return ResponseEntity.ok(storeService.search(keyword, pageable).getContent());
	}

}