/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: SHdX7hya5LumVF0W2c0gz2H0kCqJf7+5
 */
package com.futengwl.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.AftersalesDao;
import com.futengwl.dao.OrderItemDao;
import com.futengwl.entity.Aftersales;
import com.futengwl.entity.AftersalesItem;
import com.futengwl.entity.AftersalesReturns;
import com.futengwl.entity.Member;
import com.futengwl.entity.Order;
import com.futengwl.entity.OrderItem;
import com.futengwl.entity.OrderReturns;
import com.futengwl.entity.OrderReturnsItem;
import com.futengwl.entity.Store;
import com.futengwl.exception.ResourceNotFoundException;
import com.futengwl.service.AftersalesService;
import com.futengwl.service.OrderService;

/**
 * Service - 售后
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class AftersalesServiceImpl extends BaseServiceImpl<Aftersales, Long> implements AftersalesService {

	@Inject
	private AftersalesDao aftersalesDao;
	@Inject
	private OrderService orderService;
	@Inject
	private OrderItemDao orderItemDao;

	@Override
	@Transactional(readOnly = true)
	public List<Aftersales> findList(List<OrderItem> orderItems) {
		return aftersalesDao.findList(orderItems);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Aftersales> findPage(Aftersales.Type type, Aftersales.Status status, Member member, Store store, Pageable pageable) {
		return aftersalesDao.findPage(type, status, member, store, pageable);
	}

	@Override
	public void review(Aftersales aftersales, boolean passed) {
		Assert.notNull(aftersales, "[Assertion failed] - aftersales is required; it must not be null");
		Assert.state(Aftersales.Status.PENDING.equals(aftersales.getStatus()), "[Assertion failed] - aftersales status must be PENDING");

		if (passed) {
			aftersales.setStatus(Aftersales.Status.APPROVED);
		} else {
			aftersales.setStatus(Aftersales.Status.FAILED);
		}
	}

	@Override
	public void complete(Aftersales aftersales) {
		Assert.notNull(aftersales, "[Assertion failed] - aftersales is required; it must not be null");
		Assert.notEmpty(aftersales.getOrderItems(), "[Assertion failed] - aftersales orderItems must not be empty: it must contain at least 1 element");
		Assert.isTrue(Aftersales.Status.APPROVED.equals(aftersales.getStatus()), "[Assertion failed] - aftersales status must be APPROVED");

		if (Aftersales.Type.AFTERSALES_RETURNS.equals(aftersales.getType())) {
			completeReturns((AftersalesReturns) aftersales);
		}
		aftersales.setStatus(Aftersales.Status.COMPLETED);
	}

	@Override
	public void completeReturns(AftersalesReturns aftersalesReturns) {
		Assert.notNull(aftersalesReturns, "[Assertion failed] - aftersalesReturns is required; it must not be null");
		Assert.isTrue(Aftersales.Status.APPROVED.equals(aftersalesReturns.getStatus()), "[Assertion failed] - aftersalesReturns status must be APPROVED");
		Assert.state(CollectionUtils.isNotEmpty(aftersalesReturns.getOrderItems()), "[Assertion failed] - aftersalesReturns orderItems must not be empty: it must contain at least 1 element");

		List<OrderItem> orderItems = aftersalesReturns.getOrderItems();
		Order order = orderItems.get(0).getOrder();
		OrderReturns orderReturns = new OrderReturns();
		List<OrderReturnsItem> orderReturnsItems = new ArrayList<>();
		for (AftersalesItem aftersalesItem : aftersalesReturns.getAftersalesItems()) {
			OrderReturnsItem orderReturnsItem = new OrderReturnsItem();
			orderReturnsItem.setSn(aftersalesItem.getOrderItem().getSn());
			orderReturnsItem.setName(aftersalesItem.getOrderItem().getName());
			orderReturnsItem.setQuantity(aftersalesItem.getQuantity());
			orderReturnsItem.setOrderReturns(orderReturns);
			orderReturnsItem.setSpecifications(aftersalesItem.getOrderItem().getSpecifications());
			orderReturnsItems.add(orderReturnsItem);
		}
		orderReturns.setOrderReturnsItems(orderReturnsItems);
		orderReturns.setShippingMethod(order.getShippingMethod());
		orderReturns.setDeliveryCorp(aftersalesReturns.getDeliveryCorp());
		orderReturns.setTrackingNo(aftersalesReturns.getTrackingNo());
		orderReturns.setShipper(order.getMember().getDisplayName());
		orderService.returns(order, orderReturns);
	}

	@Override
	public void cancle(Aftersales aftersales) {
		Assert.notNull(aftersales, "[Assertion failed] - aftersales is required; it must not be null");
		Assert.isTrue(Aftersales.Status.PENDING.equals(aftersales.getStatus()) || Aftersales.Status.APPROVED.equals(aftersales.getStatus()), "[Assertion failed] - aftersales status must be PENDING or APPROVED");

		aftersales.setStatus(Aftersales.Status.CANCELED);
	}

	@Override
	public void filterNotActiveAftersalesItem(Aftersales aftersales) {
		for (Iterator<AftersalesItem> iterator = aftersales.getAftersalesItems().iterator(); iterator.hasNext();) {
			AftersalesItem aftersalesItem = iterator.next();
			if (aftersalesItem.getOrderItem() == null || aftersalesItem.getQuantity() == null) {
				iterator.remove();
				continue;
			}

			OrderItem orderItem = orderItemDao.find(aftersalesItem.getOrderItem().getId());
			if (orderItem == null) {
				throw new ResourceNotFoundException();
			}
			aftersalesItem.setOrderItem(orderItem);
			aftersalesItem.setAftersales(aftersales);
		}
	}

	@Override
	public boolean existsIllegalAftersalesItems(List<AftersalesItem> aftersalesItems) {

		return CollectionUtils.exists(aftersalesItems, new Predicate() {

			@Override
			public boolean evaluate(Object object) {
				AftersalesItem aftersalesItem = (AftersalesItem) object;
				return aftersalesItem == null || aftersalesItem.getOrderItem() == null || aftersalesItem.getOrderItem().getAllowApplyAftersalesQuantity() < aftersalesItem.getQuantity();
			}
		});
	}

}