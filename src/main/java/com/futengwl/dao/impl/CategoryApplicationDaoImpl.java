/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 9gCis3YfowTNC+1yExFXjnBfZ73/Tjze
 */
package com.futengwl.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.CategoryApplicationDao;
import com.futengwl.entity.CategoryApplication;
import com.futengwl.entity.CategoryApplication.Status;
import com.futengwl.entity.ProductCategory;
import com.futengwl.entity.Store;

/**
 * Dao - 经营分类申请
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class CategoryApplicationDaoImpl extends BaseDaoImpl<CategoryApplication, Long> implements CategoryApplicationDao {

	@Override
	public List<CategoryApplication> findList(Store store, ProductCategory productCategory, CategoryApplication.Status status) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CategoryApplication> criteriaQuery = criteriaBuilder.createQuery(CategoryApplication.class);
		Root<CategoryApplication> root = criteriaQuery.from(CategoryApplication.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("productCategory"), productCategory));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery);
	}

	@Override
	public Page<CategoryApplication> findPage(Status status, Store store, ProductCategory productCategory, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CategoryApplication> criteriaQuery = criteriaBuilder.createQuery(CategoryApplication.class);
		Root<CategoryApplication> root = criteriaQuery.from(CategoryApplication.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("productCategory"), productCategory));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Long count(Status status, Store store, ProductCategory productCategory) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CategoryApplication> criteriaQuery = criteriaBuilder.createQuery(CategoryApplication.class);
		Root<CategoryApplication> root = criteriaQuery.from(CategoryApplication.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("productCategory"), productCategory));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

}