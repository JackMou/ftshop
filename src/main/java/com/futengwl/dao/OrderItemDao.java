/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: pPBa5yhPTAsI5eulpPHCsuRFZLOzKmpP
 */
package com.futengwl.dao;

import com.futengwl.entity.OrderItem;

/**
 * Dao - 订单项
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface OrderItemDao extends BaseDao<OrderItem, Long> {
	
	/**
	 * 根据用户id, 商品sn查询购买数量
	 * @param memberId
	 * @param sn
	 * @return
	 */
	int queryOrderItemCount(Long memberId, String sn);
}