/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: Ru+jlwo36UMszT4Yib3BKpofvFzMBOht
 */
package com.futengwl.dao.impl;

import com.futengwl.dao.ActiveMQProducerDao;
import com.futengwl.entity.ActiveMQProducer;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

/**
 * Dao - 管理员
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class ActiveMQProducerDaoImpl extends BaseDaoImpl<ActiveMQProducer, Long> implements ActiveMQProducerDao {

    @Override
    public ActiveMQProducer getByOrderSn(String orderSn) {
        String jpql = "select activeaMQProducer from ActiveMQProducer activeaMQProducer where activeaMQProducer.orderSn=:orderSn";
        TypedQuery<ActiveMQProducer> query = entityManager.createQuery(jpql, ActiveMQProducer.class);
        return query.setParameter("orderSn", orderSn).getSingleResult();
    }
}