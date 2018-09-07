/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: JN9plkAx2PGRs/nVkR5J/a/0sMR4Cxgq
 */
package com.futengwl.dao;

import java.util.List;

import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.entity.Navigation;
import com.futengwl.entity.NavigationGroup;

/**
 * Dao - 导航
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface NavigationDao extends BaseDao<Navigation, Long> {

	/**
	 * 查找导航
	 * 
	 * @param navigationGroup
	 *            导航组
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 导航
	 */
	List<Navigation> findList(NavigationGroup navigationGroup, Integer count, List<Filter> filters, List<Order> orders);

}