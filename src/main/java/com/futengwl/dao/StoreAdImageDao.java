/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: zB6xPKQCGPA2TviHdW66xFtoG7dl/BP1
 */
package com.futengwl.dao;

import java.util.List;

import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Store;
import com.futengwl.entity.StoreAdImage;

/**
 * Dao - 店铺广告图片
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface StoreAdImageDao extends BaseDao<StoreAdImage, Long> {

	/**
	 * 查找店铺广告图片
	 * 
	 * @param store
	 *            店铺
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 店铺广告图片
	 */
	List<StoreAdImage> findList(Store store, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找店铺广告图片分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页信息
	 * @return 店铺广告图片分页
	 */
	Page<StoreAdImage> findPage(Store store, Pageable pageable);

}