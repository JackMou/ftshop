/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: sZs0UQLUZYtjSb7EDAppacUqIDqzHqFD
 */
package com.futengwl.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.dao.StoreRankDao;
import com.futengwl.entity.StoreRank;
import com.futengwl.service.StoreRankService;

/**
 * Service - 店铺等级
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class StoreRankServiceImpl extends BaseServiceImpl<StoreRank, Long> implements StoreRankService {

	@Inject
	private StoreRankDao storeRankDao;

	@Override
	@Transactional(readOnly = true)
	public boolean nameExists(String name) {
		return storeRankDao.exists("name", name);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean nameUnique(Long id, String name) {
		return storeRankDao.unique(id, "name", name);
	}

	@Override
	@Transactional(readOnly = true)
	public List<StoreRank> findList(Boolean isAllowRegister, List<Filter> filters, List<Order> orders) {
		return storeRankDao.findList(isAllowRegister, filters, orders);
	}

}