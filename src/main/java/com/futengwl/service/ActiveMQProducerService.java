/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: dZ5fkuhtO1rLVZPUt9inXzr6mNyFFZ7p
 */
package com.futengwl.service;

import com.futengwl.entity.ActiveMQProducer;
import com.futengwl.entity.Admin;
import com.futengwl.entity.PaymentItem;
import com.futengwl.entity.PaymentTransaction;
import com.futengwl.plugin.PaymentPlugin;

import java.util.Collection;

/**
 * Service - 支付事务
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface ActiveMQProducerService extends BaseService<ActiveMQProducer, Long> {

	ActiveMQProducer getByOrderSn(String orderSn);
}