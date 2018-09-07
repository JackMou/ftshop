/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 3Hzf3pCZLaoJ5tSVTghntiPNhrZKX8Zk
 */
package com.futengwl.service;

import java.math.BigDecimal;

import com.futengwl.entity.Area;
import com.futengwl.entity.Receiver;
import com.futengwl.entity.ShippingMethod;
import com.futengwl.entity.Store;

/**
 * Service - 配送方式
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface ShippingMethodService extends BaseService<ShippingMethod, Long> {

	/**
	 * 计算运费
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param store
	 *            店铺
	 * @param area
	 *            地区
	 * @param weight
	 *            重量
	 * @return 运费
	 */
	BigDecimal calculateFreight(ShippingMethod shippingMethod, Store store, Area area, Integer weight);

	/**
	 * 计算运费
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param store
	 *            店铺
	 * @param receiver
	 *            收货地址
	 * @param weight
	 *            重量
	 * @return 运费
	 */
	BigDecimal calculateFreight(ShippingMethod shippingMethod, Store store, Receiver receiver, Integer weight);
}