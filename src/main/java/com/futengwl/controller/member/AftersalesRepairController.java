/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: f63+9GtBRzk9klLAMewNVfZbXx++JahX
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
import com.futengwl.entity.AftersalesRepair;
import com.futengwl.entity.Area;
import com.futengwl.entity.Member;
import com.futengwl.entity.Order;
import com.futengwl.exception.UnauthorizedException;
import com.futengwl.security.CurrentUser;
import com.futengwl.service.AftersalesRepairService;
import com.futengwl.service.AftersalesService;
import com.futengwl.service.AreaService;
import com.futengwl.service.OrderService;

/**
 * Controller - 维修
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("memberAftersalesRepairController")
@RequestMapping("/member/aftersales_repair")
public class AftersalesRepairController extends BaseController {

	@Inject
	private AftersalesRepairService aftersalesRepairService;
	@Inject
	private OrderService orderService;
	@Inject
	private AftersalesService aftersalesService;
	@Inject
	private AreaService areaService;

	/**
	 * 添加属性
	 */
	@ModelAttribute
	public void populateModel(Long aftersalesRepairId, Long orderId, @CurrentUser Member currentUser, ModelMap model) {
		AftersalesRepair aftersalesRepair = aftersalesRepairService.find(aftersalesRepairId);
		if (aftersalesRepair != null && !currentUser.equals(aftersalesRepair.getMember())) {
			throw new UnauthorizedException();
		}
		Order order = orderService.find(orderId);
		if (order != null && !currentUser.equals(order.getMember())) {
			throw new UnauthorizedException();
		}
		model.addAttribute("aftersalesRepair", aftersalesRepair);
		model.addAttribute("order", order);
	}

	/**
	 * 维修
	 */
	@PostMapping("/repair")
	public ResponseEntity<?> repair(@ModelAttribute("aftersalesRepairForm") AftersalesRepair aftersalesRepairForm, @ModelAttribute(binding = false) Order order, Long areaId) {
		if (order == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}

		aftersalesService.filterNotActiveAftersalesItem(aftersalesRepairForm);
		if (aftersalesService.existsIllegalAftersalesItems(aftersalesRepairForm.getAftersalesItems())) {
			return Results.UNPROCESSABLE_ENTITY;
		}

		Area area = areaService.find(areaId);
		aftersalesRepairForm.setStatus(Aftersales.Status.PENDING);
		aftersalesRepairForm.setArea(area);
		aftersalesRepairForm.setMember(order.getMember());
		aftersalesRepairForm.setStore(order.getStore());

		if (!isValid(aftersalesRepairForm)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		aftersalesRepairService.save(aftersalesRepairForm);
		return Results.OK;
	}

}