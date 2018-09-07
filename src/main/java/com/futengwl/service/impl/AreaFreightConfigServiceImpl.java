/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: i8f7beXN/5qO2RdP3p1st2PGNH2o7IsG
 */
package com.futengwl.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.AreaFreightConfigDao;
import com.futengwl.entity.Area;
import com.futengwl.entity.AreaFreightConfig;
import com.futengwl.entity.ShippingMethod;
import com.futengwl.entity.Store;
import com.futengwl.service.AreaFreightConfigService;

/**
 * Service - 地区运费配置
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class AreaFreightConfigServiceImpl extends BaseServiceImpl<AreaFreightConfig, Long> implements AreaFreightConfigService {

	@Inject
	private AreaFreightConfigDao areaFreightConfigDao;

	@Override
	@Transactional(readOnly = true)
	public boolean exists(ShippingMethod shippingMethod, Store store, Area area) {
		return areaFreightConfigDao.exists(shippingMethod, store, area);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean unique(Long id, ShippingMethod shippingMethod, Store store, Area area) {
		return areaFreightConfigDao.unique(id, shippingMethod, store, area);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AreaFreightConfig> findPage(ShippingMethod shippingMethod, Store store, Pageable pageable) {
		return areaFreightConfigDao.findPage(shippingMethod, store, pageable);
	}

}