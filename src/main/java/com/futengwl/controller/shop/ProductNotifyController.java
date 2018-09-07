/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 38L1WXNdEySTvcDZT8y5jo3FscVDdHVO
 */
package com.futengwl.controller.shop;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futengwl.Results;
import com.futengwl.entity.Member;
import com.futengwl.entity.ProductNotify;
import com.futengwl.entity.Sku;
import com.futengwl.security.CurrentUser;
import com.futengwl.service.ProductNotifyService;
import com.futengwl.service.SkuService;

/**
 * Controller - 到货通知
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("shopProductNotifyController")
@RequestMapping("/product_notify")
public class ProductNotifyController extends BaseController {

	@Inject
	private ProductNotifyService productNotifyService;
	@Inject
	private SkuService skuService;

	/**
	 * 获取当前会员E-mail
	 */
	@GetMapping("/email")
	public ResponseEntity<?> email(@CurrentUser Member currentUser) {
		String email = currentUser != null ? currentUser.getEmail() : null;
		Map<String, String> data = new HashMap<>();
		data.put("email", email);
		return ResponseEntity.ok(data);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public ResponseEntity<?> save(String email, Long skuId, @CurrentUser Member currentUser) {
		if (!isValid(ProductNotify.class, "email", email)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		Sku sku = skuService.find(skuId);
		if (sku == null) {
			return Results.unprocessableEntity("shop.productNotify.skuNotExist");
		}
		if (!sku.getIsActive()) {
			return Results.unprocessableEntity("shop.productNotify.skuNotActive");
		}
		if (!sku.getIsMarketable()) {
			return Results.unprocessableEntity("shop.productNotify.skuNotMarketable");
		}
		if (!sku.getIsOutOfStock()) {
			return Results.unprocessableEntity("shop.productNotify.skuInStock");
		}
		if (productNotifyService.exists(sku, email)) {
			return Results.unprocessableEntity("shop.productNotify.exist");
		}
		ProductNotify productNotify = new ProductNotify();
		productNotify.setEmail(email);
		productNotify.setHasSent(false);
		productNotify.setMember(currentUser);
		productNotify.setSku(sku);
		productNotifyService.save(productNotify);
		return Results.OK;
	}

}