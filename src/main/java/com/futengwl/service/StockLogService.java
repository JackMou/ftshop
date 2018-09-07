/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: r6vyvafaTZszHIH7oYdpsKgKO17RRLEb
 */
package com.futengwl.service;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.StockLog;
import com.futengwl.entity.Store;

/**
 * Service - 库存记录
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface StockLogService extends BaseService<StockLog, Long> {

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