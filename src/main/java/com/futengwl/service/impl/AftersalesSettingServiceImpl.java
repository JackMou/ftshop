/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 6j+4lW0Y14h/JVw+zZSYsxJ4zVtwsVtB
 */
package com.futengwl.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.futengwl.dao.AftersalesSettingDao;
import com.futengwl.entity.AftersalesSetting;
import com.futengwl.entity.Store;
import com.futengwl.service.AftersalesSettingService;

/**
 * Service - 售后设置
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class AftersalesSettingServiceImpl extends BaseServiceImpl<AftersalesSetting, Long> implements AftersalesSettingService {

	@Inject
	private AftersalesSettingDao aftersalesSettingDao;

	@Override
	@Transactional(readOnly = true)
	public AftersalesSetting findByStore(Store store) {
		Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");

		return aftersalesSettingDao.find("store", store);
	}

}