/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: wmTEPJ45sDfy4SPohQnzaRo90KuBSZYY
 */
package com.futengwl.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.dao.BrandDao;
import com.futengwl.entity.Brand;
import com.futengwl.entity.ProductCategory;

/**
 * Dao - 品牌
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class BrandDaoImpl extends BaseDaoImpl<Brand, Long> implements BrandDao {

	@Override
	public List<Brand> findList(ProductCategory productCategory, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
		Root<Brand> root = criteriaQuery.from(Brand.class);
		criteriaQuery.select(root);
		if (productCategory != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.join("productCategories"), productCategory));
		}
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

}