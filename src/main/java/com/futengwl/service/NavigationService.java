/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: w+Rxg7qOzi05LlCcGvEsMCS1yqrkor8G
 */
package com.futengwl.service;

import java.util.List;

import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.entity.Navigation;

/**
 * Service - 导航
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface NavigationService extends BaseService<Navigation, Long> {

	/**
	 * 查找导航
	 * 
	 * @param navigationGroupId
	 *            导航组ID
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 导航
	 */
	List<Navigation> findList(Long navigationGroupId, Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

}