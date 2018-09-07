/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 9KiSTbikBJ+wz4JgR5/5YiJykRu/RNyT
 */
package com.futengwl.dao;

import java.util.Collection;

import com.futengwl.entity.PaymentTransaction;
import com.futengwl.plugin.PaymentPlugin;

/**
 * Dao - 支付事务
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface PaymentTransactionDao extends BaseDao<PaymentTransaction, Long> {

	/**
	 * 查找可用支付事务
	 * 
	 * @param lineItem
	 *            支付明细
	 * @param paymentPlugin
	 *            支付插件
	 * @return 可用支付事务，若不存在则返回null
	 */
	PaymentTransaction findAvailable(PaymentTransaction.LineItem lineItem, PaymentPlugin paymentPlugin);

	/**
	 * 查找可用父支付事务
	 * 
	 * @param lineItems
	 *            支付明细
	 * @param paymentPlugin
	 *            支付插件
	 * @return 可用父支付事务，若不存在则返回null
	 */
	PaymentTransaction findAvailableParent(Collection<PaymentTransaction.LineItem> lineItems, PaymentPlugin paymentPlugin);

}