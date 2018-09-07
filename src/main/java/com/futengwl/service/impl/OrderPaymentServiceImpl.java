/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: pHg+SvPcHj4zKyKstuyHbPImrpO56LiO
 */
package com.futengwl.service.impl;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.futengwl.dao.OrderPaymentDao;
import com.futengwl.dao.SnDao;
import com.futengwl.entity.OrderPayment;
import com.futengwl.entity.Sn;
import com.futengwl.service.OrderPaymentService;

/**
 * Service - 订单支付
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class OrderPaymentServiceImpl extends BaseServiceImpl<OrderPayment, Long> implements OrderPaymentService {

	@Inject
	private OrderPaymentDao orderPaymentDao;
	@Inject
	private SnDao snDao;

	@Override
	@Transactional(readOnly = true)
	public OrderPayment findBySn(String sn) {
		return orderPaymentDao.find("sn", StringUtils.lowerCase(sn));
	}

	@Override
	@Transactional
	public OrderPayment save(OrderPayment orderPayment) {
		Assert.notNull(orderPayment, "[Assertion failed] - orderPayment is required; it must not be null");

		orderPayment.setSn(snDao.generate(Sn.Type.ORDER_PAYMENT));

		return super.save(orderPayment);
	}

}