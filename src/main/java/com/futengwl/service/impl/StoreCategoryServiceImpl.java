/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: syjrDODUanByV3B3ztcB9Zd4/jBy7xpU
 */
package com.futengwl.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futengwl.dao.StoreCategoryDao;
import com.futengwl.entity.StoreCategory;
import com.futengwl.service.StoreCategoryService;

/**
 * Service - 店铺分类
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class StoreCategoryServiceImpl extends BaseServiceImpl<StoreCategory, Long> implements StoreCategoryService {

	@Inject
	private StoreCategoryDao storeCategoryDao;

	@Override
	@Transactional(readOnly = true)
	public boolean nameExists(String name) {
		return storeCategoryDao.exists("name", name, true);
	}

}