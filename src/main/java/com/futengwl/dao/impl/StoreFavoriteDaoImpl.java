/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: EYPzyfQbHf0ARlRPrge3V5hTZv2KaTjr
 */
package com.futengwl.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.StoreFavoriteDao;
import com.futengwl.entity.Member;
import com.futengwl.entity.Store;
import com.futengwl.entity.StoreFavorite;

/**
 * Dao - 店铺收藏
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class StoreFavoriteDaoImpl extends BaseDaoImpl<StoreFavorite, Long> implements StoreFavoriteDao {

	@Override
	public boolean exists(Member member, Store store) {
		String jpql = "select count(*) from StoreFavorite storeFavorite where storeFavorite.member = :member and storeFavorite.store = :store";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("member", member).setParameter("store", store).getSingleResult();
		return count > 0;
	}

	@Override
	public List<StoreFavorite> findList(Member member, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StoreFavorite> criteriaQuery = criteriaBuilder.createQuery(StoreFavorite.class);
		Root<StoreFavorite> root = criteriaQuery.from(StoreFavorite.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	@Override
	public Page<StoreFavorite> findPage(Member member, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StoreFavorite> criteriaQuery = criteriaBuilder.createQuery(StoreFavorite.class);
		Root<StoreFavorite> root = criteriaQuery.from(StoreFavorite.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Long count(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StoreFavorite> criteriaQuery = criteriaBuilder.createQuery(StoreFavorite.class);
		Root<StoreFavorite> root = criteriaQuery.from(StoreFavorite.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery);
	}

}