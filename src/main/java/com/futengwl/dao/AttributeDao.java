/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: wj5ojUwvOqOHh9NcjcxL5C9eEfwolgm6
 */
package com.futengwl.dao;

import java.util.List;

import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.entity.Attribute;
import com.futengwl.entity.ProductCategory;

/**
 * Dao - 属性
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface AttributeDao extends BaseDao<Attribute, Long> {

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

}