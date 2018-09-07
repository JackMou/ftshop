/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: LMQK31T1WK1pRnOjfXtyAw5TyWafM3Sd
 */
package com.futengwl.service;

import java.util.List;
import java.util.Map;

import com.futengwl.entity.OrderShipping;

/**
 * Service - 订单发货
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface OrderShippingService extends BaseService<OrderShipping, Long> {

	/**
	 * 根据编号查找订单发货
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 订单发货，若不存在则返回null
	 */
	OrderShipping findBySn(String sn);

	/**
	 * 获取物流动态
	 * 
	 * @param orderShipping
	 *            订单发货
	 * @return 物流动态
	 */
	List<Map<String, String>> getTransitSteps(OrderShipping orderShipping);

	/**
	 * 获取物流动态
	 * 
	 * @param deliveryCorpCode
	 *            物流公司代码
	 * @param trackingNo
	 *            运单号
	 * @return 物流动态
	 */
	List<Map<String, String>> getTransitSteps(String deliveryCorpCode, String trackingNo);

}