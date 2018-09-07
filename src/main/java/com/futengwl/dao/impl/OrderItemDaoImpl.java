/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: TQIa8sOfHrY+VgfQiz/jt9qg9WtEfpTr
 */
package com.futengwl.dao.impl;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.futengwl.dao.OrderItemDao;
import com.futengwl.entity.OrderItem;

/**
 * Dao - 订单项
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class OrderItemDaoImpl extends BaseDaoImpl<OrderItem, Long> implements OrderItemDao {

	@Override
	public int queryOrderItemCount(Long memberId, String sn) {
		String jpql = "SELECT COUNT(i.id) FROM OrderItem i WHERE i.sn=:sn AND i.order.member.id=:memberId AND i.order.status IN (0,1,2,3,4,5)";
		TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
		return query.setParameter("sn", sn).setParameter("memberId", memberId).getSingleResult().intValue();
	}

}