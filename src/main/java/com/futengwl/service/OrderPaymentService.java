/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: Sa4Cgh0cG9YUY0iN2isQTT/uNcE+kjX/
 */
package com.futengwl.service;

import com.futengwl.entity.OrderPayment;

/**
 * Service - 订单支付
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface OrderPaymentService extends BaseService<OrderPayment, Long> {

	/**
	 * 根据编号查找订单支付
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 订单支付，若不存在则返回null
	 */
	OrderPayment findBySn(String sn);

}