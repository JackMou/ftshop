/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: ISf7MmSZhXXkmyXMwMI3WH57mPNND9Yh
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
import com.futengwl.service.OrderReturnsService;

/**
 * Controller - 订单退货
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("adminOrderReturnsController")
@RequestMapping("/admin/order_returns")
public class OrderReturnsController extends BaseController {

	@Inject
	private OrderReturnsService orderReturnsService;

	/**
	 * 查看
	 */
	@GetMapping("/view")
	public String view(Long id, ModelMap model) {
		model.addAttribute("returns", orderReturnsService.find(id));
		return "admin/order_returns/view";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", orderReturnsService.findPage(pageable));
		return "admin/order_returns/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public ResponseEntity<?> delete(Long[] ids) {
		orderReturnsService.delete(ids);
		return Results.OK;
	}

}