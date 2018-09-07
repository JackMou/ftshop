/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: TEe5eWM/d2qiH/btT71DmfHQl/0wLjZ0
 */
package com.futengwl.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.DeliveryTemplateDao;
import com.futengwl.entity.DeliveryTemplate;
import com.futengwl.entity.Store;

/**
 * Dao - 快递单模板
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class DeliveryTemplateDaoImpl extends BaseDaoImpl<DeliveryTemplate, Long> implements DeliveryTemplateDao {

	@Override
	public DeliveryTemplate findDefault(Store store) {
		Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");

		try {
			String jpql = "select deliveryTemplate from DeliveryTemplate deliveryTemplate where deliveryTemplate.isDefault = true and lower(deliveryTemplate.store) = lower(:store)";
			return entityManager.createQuery(jpql, DeliveryTemplate.class).setParameter("store", store).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<DeliveryTemplate> findList(Store store) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DeliveryTemplate> criteriaQuery = criteriaBuilder.createQuery(DeliveryTemplate.class);
		Root<DeliveryTemplate> root = criteriaQuery.from(DeliveryTemplate.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery);
	}

	@Override
	public Page<DeliveryTemplate> findPage(Store store, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DeliveryTemplate> criteriaQuery = criteriaBuilder.createQuery(DeliveryTemplate.class);
		Root<DeliveryTemplate> root = criteriaQuery.from(DeliveryTemplate.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public void clearDefault(Store store) {
		Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");

		String jpql = "update DeliveryTemplate deliveryTemplate set deliveryTemplate.isDefault = false where deliveryTemplate.isDefault = true and lower(deliveryTemplate.store) = lower(:store)";
		entityManager.createQuery(jpql).setParameter("store", store).executeUpdate();
	}

	@Override
	public void clearDefault(DeliveryTemplate exclude) {
		Assert.notNull(exclude, "[Assertion failed] - exclude is required; it must not be null");

		String jpql = "update DeliveryTemplate deliveryTemplate set deliveryTemplate.isDefault = false where deliveryTemplate.isDefault = true and deliveryTemplate != :exclude and lower(deliveryTemplate.store) = lower(:store)";
		entityManager.createQuery(jpql).setParameter("exclude", exclude).setParameter("store", exclude.getStore()).executeUpdate();
	}

}