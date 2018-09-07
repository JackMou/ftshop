/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: naTqzNQ72l2BDO7RC+E0S5spIW1GGU25
 */
package com.futengwl.dao;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.StockLog;
import com.futengwl.entity.Store;

/**
 * Dao - 库存记录
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface StockLogDao extends BaseDao<StockLog, Long> {

	/**
	 * 查找库存记录分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页
	 * @return 库存记录分页
	 */
	Page<StockLog> findPage(Store store, Pageable pageable);

}