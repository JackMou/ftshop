/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: NXCCJkYwkSKyJK1yxGt9mrMegqJrqC9F
 */
package com.futengwl.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.futengwl.dao.SnDao;
import com.futengwl.entity.OrderRefunds;
import com.futengwl.entity.Sn;
import com.futengwl.service.OrderRefundsService;

/**
 * Service - 订单退款
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class OrderRefundsServiceImpl extends BaseServiceImpl<OrderRefunds, Long> implements OrderRefundsService {

	@Inject
	private SnDao snDao;

	@Override
	@Transactional
	public OrderRefunds save(OrderRefunds orderRefunds) {
		Assert.notNull(orderRefunds, "[Assertion failed] - orderRefunds is required; it must not be null");

		orderRefunds.setSn(snDao.generate(Sn.Type.ORDER_REFUNDS));

		return super.save(orderRefunds);
	}

}