/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 4rEkRuY3D8uD2RZtIn1OhwKK4YTLSFVF
 */
package com.futengwl.dao;

import java.util.List;

import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.entity.BusinessAttribute;

/**
 * Dao - 商家注册项
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface BusinessAttributeDao extends BaseDao<BusinessAttribute, Long> {

	/**
	 * 查找未使用的对象属性序号
	 * 
	 * @return 未使用的对象属性序号，若无可用序号则返回null
	 */
	Integer findUnusedPropertyIndex();

	/**
	 * 查找商家注册项
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 会员注册项
	 */
	List<BusinessAttribute> findList(Boolean isEnabled, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 清空商家注册项值
	 * 
	 * @param businessAttribute
	 *            商家注册项
	 */
	void clearAttributeValue(BusinessAttribute businessAttribute);

}