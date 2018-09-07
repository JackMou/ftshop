/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 8YCIHwWkyhQ7mn4OhCGSean+kH3XCuEO
 */
package com.futengwl.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.dao.AttributeDao;
import com.futengwl.entity.Attribute;
import com.futengwl.entity.Product;
import com.futengwl.entity.ProductCategory;

/**
 * Dao - 属性
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class AttributeDaoImpl extends BaseDaoImpl<Attribute, Long> implements AttributeDao {

	@Override
	public Integer findUnusedPropertyIndex(ProductCategory productCategory) {
		Assert.notNull(productCategory, "[Assertion failed] - productCategory is required; it must not be null");

		for (int i = 0; i < Product.ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
			String jpql = "select count(*) from Attribute attribute where attribute.productCategory = :productCategory and attribute.propertyIndex = :propertyIndex";
			Long count = entityManager.createQuery(jpql, Long.class)
					.setParameter("productCategory", productCategory)
					.setParameter("propertyIndex", i)
					.getSingleResult();
			if (count == 0) {
				return i;
			}
		}
		return null;
	}

	@Override
	public List<Attribute> findList(ProductCategory productCategory, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Attribute> criteriaQuery = criteriaBuilder.createQuery(Attribute.class);
		Root<Attribute> root = criteriaQuery.from(Attribute.class);
		criteriaQuery.select(root);
		if (productCategory != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.get("productCategory"), productCategory));
		}
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

}