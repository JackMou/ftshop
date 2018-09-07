/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: bRHlCOOeMrIkImVJ6BB7OnTrDthRa72o
 */
package com.futengwl.dao;

import java.util.List;

import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Member;
import com.futengwl.entity.Store;
import com.futengwl.entity.StoreFavorite;

/**
 * Dao - 店铺收藏
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface StoreFavoriteDao extends BaseDao<StoreFavorite, Long> {

	/**
	 * 判断店铺收藏是否存在
	 * 
	 * @param member
	 *            会员
	 * @param store
	 *            店铺
	 * @return 店铺收藏是否存在
	 */
	boolean exists(Member member, Store store);

	/**
	 * 查找店铺收藏
	 * 
	 * @param member
	 *            会员
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 店铺收藏
	 */
	List<StoreFavorite> findList(Member member, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找店铺收藏分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 店铺收藏分页
	 */
	Page<StoreFavorite> findPage(Member member, Pageable pageable);

	/**
	 * 查找店铺收藏数量
	 * 
	 * @param member
	 *            会员
	 * @return 店铺收藏数量
	 */
	Long count(Member member);

}