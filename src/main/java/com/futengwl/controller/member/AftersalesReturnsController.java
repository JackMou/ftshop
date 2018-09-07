/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: WZOHJ6IcCG1pZ+A0bkCBQOi1+mDggtVP
 */
package com.futengwl.controller.member;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futengwl.Results;
import com.futengwl.entity.Aftersales;
import com.futengwl.entity.AftersalesReturns;
import com.futengwl.entity.Member;
import com.futengwl.entity.Order;
import com.futengwl.exception.UnauthorizedException;
import com.futengwl.security.CurrentUser;
import com.futengwl.service.AftersalesReturnsService;
import com.futengwl.service.AftersalesService;
import com.futengwl.service.OrderService;

/**
 * Controller - 退货
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("memberAftersalesReturnsController")
@RequestMapping("/member/aftersales_returns")
public class AftersalesReturnsController extends BaseController {

	@Inject
	private AftersalesReturnsService aftersalesReturnsService;
	@Inject
	private OrderService orderService;
	@Inject
	private AftersalesService aftersalesService;

	/**
	 * 添加属性
	 */
	@ModelAttribute
	public void populateModel(Long aftersalesReturnsId, Long orderId, @CurrentUser Member currentUser, ModelMap model) {
		AftersalesReturns aftersalesReturns = aftersalesReturnsService.find(aftersalesReturnsId);
		if (aftersalesReturns != null && !currentUser.equals(aftersalesReturns.getMember())) {
			throw new UnauthorizedException();
		}
		Order order = orderService.find(orderId);
		if (order != null && !currentUser.equals(order.getMember())) {
			throw new UnauthorizedException();
		}
		model.addAttribute("aftersalesReturns", aftersalesReturns);
		model.addAttribute("order", order);
	}

	/**
	 * 退货
	 */
	@PostMapping("/returns")
	public ResponseEntity<?> returns(@ModelAttribute("aftersalesReturnsForm") AftersalesReturns aftersalesReturnsForm, @ModelAttribute(binding = false) Order order) {
		if (order == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}

		aftersalesService.filterNotActiveAftersalesItem(aftersalesReturnsForm);
		if (aftersalesService.existsIllegalAftersalesItems(aftersalesReturnsForm.getAftersalesItems())) {
			return Results.UNPROCESSABLE_ENTITY;
		}

		aftersalesReturnsForm.setStatus(Aftersales.Status.PENDING);
		aftersalesReturnsForm.setMember(order.getMember());
		aftersalesReturnsForm.setStore(order.getStore());

		if (!isValid(aftersalesReturnsForm)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		aftersalesReturnsService.save(aftersalesReturnsForm);
		return Results.OK;
	}

}