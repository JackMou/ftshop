/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: ZpeFIqFOBCdBhVPmD8SoN/CUi0u6+/cY
 */
package com.futengwl.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.AftersalesDao;
import com.futengwl.entity.Aftersales;
import com.futengwl.entity.Member;
import com.futengwl.entity.OrderItem;
import com.futengwl.entity.Store;

/**
 * Dao - 售后
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class AftersalesDaoImpl extends BaseDaoImpl<Aftersales, Long> implements AftersalesDao {

	@Override
	public List<Aftersales> findList(List<OrderItem> orderItems) {
		String jpql = "select aftersales from Aftersales aftersales where aftersales in(select aftersalesItem.aftersales from AftersalesItem aftersalesItem where aftersalesItem.orderItem in(:orderItems))";
		TypedQuery<Aftersales> query = entityManager.createQuery(jpql, Aftersales.class);
		return query.setParameter("orderItems", orderItems).getResultList();
	}

	@Override
	public Page<Aftersales> findPage(Aftersales.Type type, Aftersales.Status status, Member member, Store store, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Aftersales> criteriaQuery = criteriaBuilder.createQuery(Aftersales.class);

		//Root<? extends Aftersales> root = criteriaQuery.from( ? type.getClazz() : Aftersales.class);

		Root<? extends Aftersales> root;
		if(type != null){
			root=criteriaQuery.from(type.getClazz());
		}else{
			root=criteriaQuery.from(Aftersales.class);
		}
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		criteriaQuery.where(restrictions);

		return super.findPage(criteriaQuery, pageable);
	}

}