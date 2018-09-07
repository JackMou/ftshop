/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: DsZpj8e/40R4ZhocLvyNfHEU8u5CO0QV
 */
package com.futengwl.dao;

import com.futengwl.entity.ActiveMQProducer;

/**
 * Dao - activemq生产数据
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface ActiveMQProducerDao extends BaseDao<ActiveMQProducer, Long> {
    ActiveMQProducer getByOrderSn(String orderSn);
}