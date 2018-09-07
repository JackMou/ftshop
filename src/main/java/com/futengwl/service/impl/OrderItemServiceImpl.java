/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: mUXLYwxmmOBpTD5HMJT2NCzCKhBSjN1w
 */
package com.futengwl.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.futengwl.dao.OrderItemDao;
import com.futengwl.entity.OrderItem;
import com.futengwl.service.OrderItemService;

/**
 * Service - 订单项
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem, Long> implements OrderItemService {

	@Inject
	OrderItemDao orderItemDao;
	
	@Override
	public boolean isBuy(Long memberId, String sn) {
		if(orderItemDao.queryOrderItemCount(memberId, sn) > 0) {
			return true;
		} else {
			return false;
		}
	}

}