/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: erwkppL8Av6YiHp3/OyJudyGqChyZn+H
 */
package com.futengwl.service;

import java.util.List;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Store;
import com.futengwl.entity.StoreProductCategory;

/**
 * Service - 店铺商品分类
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface StoreProductCategoryService extends BaseService<StoreProductCategory, Long> {

	/**
	 * 查找顶级店铺商品分类
	 * 
	 * @param storeId
	 *            店铺ID
	 * @return 顶级店铺商品分类
	 */
	List<StoreProductCategory> findRoots(Long storeId);

	/**
	 * 查找顶级店铺商品分类
	 * 
	 * @param storeId
	 *            店铺ID
	 * @param count
	 *            数量
	 * @return 顶级店铺商品分类
	 */
	List<StoreProductCategory> findRoots(Long storeId, Integer count);

	/**
	 * 查找顶级店铺商品分类
	 * 
	 * @param storeId
	 *            店铺ID
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 顶级店铺商品分类
	 */
	List<StoreProductCategory> findRoots(Long storeId, Integer count, boolean useCache);

	/**
	 * 查找上级店铺商品分类
	 * 
	 * @param storeProductCategoryId
	 *            店铺商品分类ID
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 上级店铺商品分类
	 */
	List<StoreProductCategory> findParents(Long storeProductCategoryId, boolean recursive, Integer count, boolean useCache);

	/**
	 * 查找店铺商品分类树
	 * 
	 * @param store
	 *            店铺
	 * @return 店铺商品分类树
	 */
	List<StoreProductCategory> findTree(Store store);

	/**
	 * 查找下级店铺商品分类
	 * 
	 * @param storeProductCategory
	 *            店铺商品分类
	 * @param store
	 *            店铺
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级店铺商品分类
	 */
	List<StoreProductCategory> findChildren(StoreProductCategory storeProductCategory, Store store, boolean recursive, Integer count);

	/**
	 * 查找下级店铺商品分类
	 * 
	 * @param storeProductCategoryId
	 *            店铺商品分类ID
	 * @param storeId
	 *            店铺ID
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 下级店铺商品分类
	 */
	List<StoreProductCategory> findChildren(Long storeProductCategoryId, Long storeId, boolean recursive, Integer count, boolean useCache);

	/**
	 * 查找店铺商品分类
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页
	 * @return 店铺商品分类
	 */
	Page<StoreProductCategory> findPage(Store store, Pageable pageable);

}