/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: e9Tlw3DtyYl3FIi+lBgvDfmp5yXTOW6D
 */
package com.futengwl.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.futengwl.dao.OrderShippingDao;
import com.futengwl.entity.Order;
import com.futengwl.entity.OrderShipping;

/**
 * Dao - 订单发货
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class OrderShippingDaoImpl extends BaseDaoImpl<OrderShipping, Long> implements OrderShippingDao {

	@Override
	public OrderShipping findLast(Order order) {
		if (order == null) {
			return null;
		}
		String jpql = "select orderShipping from OrderShipping orderShipping where lower(orderShipping.order.id) = lower(:order) order by orderShipping.createdDate desc";
		try {
			return entityManager.createQuery(jpql, OrderShipping.class).setParameter("order", order.getId()).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}