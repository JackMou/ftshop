/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: XmdhsnvY6J5REYUYKAw2GHMLFqey3aWi
 */
package com.futengwl.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.futengwl.Order;
import com.futengwl.dao.SnDao;
import com.futengwl.dao.SvcDao;
import com.futengwl.entity.Sn;
import com.futengwl.entity.Store;
import com.futengwl.entity.StoreRank;
import com.futengwl.entity.Svc;
import com.futengwl.service.SvcService;

/**
 * Service - 服务
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class SvcServiceImpl extends BaseServiceImpl<Svc, Long> implements SvcService {

	@Inject
	private SvcDao svcDao;
	@Inject
	private SnDao snDao;

	@Override
	@Transactional(readOnly = true)
	public Svc findBySn(String sn) {
		return svcDao.find("sn", StringUtils.lowerCase(sn));
	}

	@Override
	@Transactional(readOnly = true)
	public Svc findTheLatest(Store store, String promotionPluginId, StoreRank storeRank) {

		List<Order> orderList = new ArrayList<>();
		orderList.add(new Order("createdDate", Order.Direction.DESC));
		List<Svc> serviceOrders = svcDao.find(store, promotionPluginId, storeRank, orderList);

		return CollectionUtils.isNotEmpty(serviceOrders) ? serviceOrders.get(0) : null;
	}

	@Override
	@Transactional
	public Svc save(Svc svc) {
		Assert.notNull(svc, "[Assertion failed] - svc is required; it must not be null");

		svc.setSn(snDao.generate(Sn.Type.PLATFORM_SERVICE));

		return super.save(svc);
	}

}