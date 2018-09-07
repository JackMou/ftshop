/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: FI8oso6ct2EfejPnSW7v3Muqvdm/9bRe
 */
package com.futengwl.dao.impl;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.DefaultFreightConfigDao;
import com.futengwl.entity.Area;
import com.futengwl.entity.DefaultFreightConfig;
import com.futengwl.entity.ShippingMethod;
import com.futengwl.entity.Store;

/**
 * Dao - 默认运费配置
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class DefaultFreightConfigDaoImpl extends BaseDaoImpl<DefaultFreightConfig, Long> implements DefaultFreightConfigDao {

	@Override
	public boolean exists(ShippingMethod shippingMethod, Area area) {
		if (shippingMethod == null || area == null) {
			return false;
		}
		String jpql = "select count(*) from AreaFreightConfig areaFreightConfig where areaFreightConfig.shippingMethod = :shippingMethod and areaFreightConfig.area = :area";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("shippingMethod", shippingMethod).setParameter("area", area).getSingleResult();
		return count > 0;
	}

	@Override
	public Page<DefaultFreightConfig> findPage(Store store, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DefaultFreightConfig> criteriaQuery = criteriaBuilder.createQuery(DefaultFreightConfig.class);
		Root<DefaultFreightConfig> root = criteriaQuery.from(DefaultFreightConfig.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public DefaultFreightConfig find(ShippingMethod shippingMethod, Store store) {
		if (shippingMethod == null || store == null) {
			return null;
		}
		String jpql = "select defaultFreightConfig from DefaultFreightConfig defaultFreightConfig where defaultFreightConfig.store = :store and defaultFreightConfig.shippingMethod = :shippingMethod order by defaultFreightConfig.store asc";
		try {
			return entityManager.createQuery(jpql, DefaultFreightConfig.class).setParameter("store", store).setParameter("shippingMethod", shippingMethod).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}