/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: ab7fQ2f5tIK8W1vDodZrOdBIOJqB/CIU
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
import com.futengwl.entity.BaseEntity;
import com.futengwl.entity.ProductTag;
import com.futengwl.service.ProductTagService;

/**
 * Controller - 商品标签
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("adminProductTagController")
@RequestMapping("/admin/product_tag")
public class ProductTagController extends BaseController {

	@Inject
	private ProductTagService productTagService;

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		return "admin/product_tag/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public ResponseEntity<?> save(ProductTag productTag) {
		if (!isValid(productTag, BaseEntity.Save.class)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		productTag.setProducts(null);
		productTagService.save(productTag);
		return Results.OK;
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("productTag", productTagService.find(id));
		return "admin/product_tag/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(ProductTag productTag) {
		if (!isValid(productTag)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		productTagService.update(productTag, "products");
		return Results.OK;
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", productTagService.findPage(pageable));
		return "admin/product_tag/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public ResponseEntity<?> delete(Long[] ids) {
		productTagService.delete(ids);
		return Results.OK;
	}

}