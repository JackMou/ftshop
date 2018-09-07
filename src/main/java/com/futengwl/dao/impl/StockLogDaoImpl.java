/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: d7Zv/fAhKUtzKuGYKC+Nh7XuDXMvk0Te
 */
package com.futengwl.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.StockLogDao;
import com.futengwl.entity.StockLog;
import com.futengwl.entity.Store;

/**
 * Dao - 库存记录
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class StockLogDaoImpl extends BaseDaoImpl<StockLog, Long> implements StockLogDao {

	@Override
	public Page<StockLog> findPage(Store store, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StockLog> criteriaQuery = criteriaBuilder.createQuery(StockLog.class);
		Root<StockLog> root = criteriaQuery.from(StockLog.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("sku").get("product").get("store"), store));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

}