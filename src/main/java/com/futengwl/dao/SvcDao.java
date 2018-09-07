/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 92O0P7s2wtoq4SZ4NvnxlEUAerI65IDF
 */
package com.futengwl.dao;

import java.util.List;

import com.futengwl.Order;
import com.futengwl.entity.Store;
import com.futengwl.entity.StoreRank;
import com.futengwl.entity.Svc;

/**
 * Dao - 服务
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface SvcDao extends BaseDao<Svc, Long> {

	/**
	 * 查找服务
	 * 
	 * @param store
	 *            店铺
	 * @param promotionPluginId
	 *            促销插件Id
	 * @param storeRank
	 *            店铺等级
	 * @param orders
	 *            排序
	 * @return 服务
	 */
	List<Svc> find(Store store, String promotionPluginId, StoreRank storeRank, List<Order> orders);

}