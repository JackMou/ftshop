/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: XGiHcE13Ms3BDR8fSttsRctY1wYngRcC
 */
package com.futengwl.service;

import com.futengwl.entity.OrderItem;

/**
 * Service - 订单项
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface OrderItemService extends BaseService<OrderItem, Long> {
	
	/**
	 * 判断用户是否购买过某商品
	 * @param memberId
	 * @param sn
	 * @return
	 */
	public boolean isBuy(Long memberId, String sn);
}