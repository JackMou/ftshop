/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: fg8iqrMZFf8W+G8WmR/9rpxOz1kQKGa0
 */
package com.futengwl.service;

import java.util.List;

import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.entity.Attribute;
import com.futengwl.entity.ProductCategory;

/**
 * Service - 属性
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface AttributeService extends BaseService<Attribute, Long> {

	/**
	 * 查找未使用的属性序号
	 * 
	 * @param productCategory
	 *            商品分类
	 * @return 未使用的属性序号，若不存在则返回null
	 */
	Integer findUnusedPropertyIndex(ProductCategory productCategory);

	/**
	 * 查找属性
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 属性
	 */
	List<Attribute> findList(ProductCategory productCategory, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找属性
	 * 
	 * @param productCategoryId
	 *            商品分类ID
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 属性
	 */
	List<Attribute> findList(Long productCategoryId, Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

	/**
	 * 转换为属性值
	 * 
	 * @param attribute
	 *            属性
	 * @param value
	 *            值
	 * @return 属性值
	 */
	String toAttributeValue(Attribute attribute, String value);

}