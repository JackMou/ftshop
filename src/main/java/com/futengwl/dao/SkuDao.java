/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 2yi7NUjdK8l2gN4aaHg5Sydb6aKUbS9z
 */
package com.futengwl.dao;

import java.util.List;
import java.util.Set;

import com.futengwl.entity.Product;
import com.futengwl.entity.Sku;
import com.futengwl.entity.Store;

/**
 * Dao - SKU
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface SkuDao extends BaseDao<Sku, Long> {

	/**
	 * 通过编号、名称查找SKU
	 * 
	 * @param store
	 *            店铺
	 * @param type
	 *            类型
	 * @param keyword
	 *            关键词
	 * @param excludes
	 *            排除SKU
	 * @param count
	 *            数量
	 * @return SKU
	 */
	List<Sku> search(Store store, Product.Type type, String keyword, Set<Sku> excludes, Integer count);

}