/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: bX99dla/nxuQTuIIxhvud3xD5asSd6rw
 */
package com.futengwl.dao;

import java.util.List;
import java.util.Set;

import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.MemberRank;
import com.futengwl.entity.ProductCategory;
import com.futengwl.entity.Promotion;
import com.futengwl.entity.Store;

/**
 * Dao - 促销
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface PromotionDao extends BaseDao<Promotion, Long> {

	/**
	 * 通过名称查找促销
	 * 
	 * @param keyword
	 *            关键词
	 * @param excludes
	 *            排除促销
	 * @param count
	 *            数量
	 * @return 促销
	 */
	List<Promotion> search(String keyword, Set<Promotion> excludes, Integer count);

	/**
	 * 查找促销
	 * 
	 * @param store
	 *            店铺
	 * @param type
	 *            类型
	 * @param isEnabled
	 *            是否开启
	 * @return 促销
	 */
	List<Promotion> findList(Store store, Promotion.Type type, Boolean isEnabled);

	/**
	 * 查找促销
	 * 
	 * @param store
	 *            店铺
	 * @param type
	 *            类型
	 * @param memberRank
	 *            会员等级
	 * @param productCategory
	 *            商品分类
	 * @param hasBegun
	 *            是否已开始
	 * @param hasEnded
	 *            是否已结束
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 促销
	 */
	List<Promotion> findList(Store store, Promotion.Type type, MemberRank memberRank, ProductCategory productCategory, Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找促销
	 * 
	 * @param store
	 *            店铺
	 * @param type
	 *            类型
	 * @param pageable
	 *            分页
	 * @return 促销分页
	 */
	Page<Promotion> findPage(Store store, Promotion.Type type, Pageable pageable);

}