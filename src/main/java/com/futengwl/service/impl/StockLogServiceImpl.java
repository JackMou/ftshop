/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: zPnRxPrr1RrbpZS2tMZu0O2eI6EEK8kn
 */
package com.futengwl.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.StockLogDao;
import com.futengwl.entity.StockLog;
import com.futengwl.entity.Store;
import com.futengwl.service.StockLogService;

/**
 * Service - 库存记录
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class StockLogServiceImpl extends BaseServiceImpl<StockLog, Long> implements StockLogService {

	@Inject
	private StockLogDao stockLogDao;

	@Override
	@Transactional(readOnly = true)
	public Page<StockLog> findPage(Store store, Pageable pageable) {
		return stockLogDao.findPage(store, pageable);
	}

}