/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: URWLMwBkKN+cpJQsCRzn+2S8HRf/KAdX
 */
package com.futengwl.dao;

import com.futengwl.entity.Order;
import com.futengwl.entity.OrderShipping;

/**
 * Dao - 订单发货
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface OrderShippingDao extends BaseDao<OrderShipping, Long> {

	/**
	 * 查找最后一条订单发货
	 * 
	 * @param order
	 *            订单
	 * @return 订单发货，若不存在则返回null
	 */
	OrderShipping findLast(Order order);

}