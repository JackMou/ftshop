/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 62QUdwgxx6n+8bsV0EGb3gVq93WIuqU2
 */
package com.futengwl.service;

import java.util.List;

import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.entity.ProductTag;

/**
 * Service - 商品标签
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface ProductTagService extends BaseService<ProductTag, Long> {

	/**
	 * 查找商品标签
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 商品标签
	 */
	List<ProductTag> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

}