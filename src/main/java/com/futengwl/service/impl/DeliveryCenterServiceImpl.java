/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 9EWJ3E2/cXK18iK/15bjhhBOJDE9Zfit
 */
package com.futengwl.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.DeliveryCenterDao;
import com.futengwl.entity.DeliveryCenter;
import com.futengwl.entity.Store;
import com.futengwl.service.DeliveryCenterService;

/**
 * Service - 发货点
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class DeliveryCenterServiceImpl extends BaseServiceImpl<DeliveryCenter, Long> implements DeliveryCenterService {

	@Inject
	private DeliveryCenterDao deliveryCenterDao;

	@Override
	@Transactional(readOnly = true)
	public DeliveryCenter findDefault(Store store) {
		return deliveryCenterDao.findDefault(store);
	}

	@Override
	@Transactional
	public DeliveryCenter save(DeliveryCenter deliveryCenter) {
		Assert.notNull(deliveryCenter, "[Assertion failed] - deliveryCenter is required; it must not be null");

		if (BooleanUtils.isTrue(deliveryCenter.getIsDefault())) {
			deliveryCenterDao.clearDefault(deliveryCenter.getStore());
		}
		return super.save(deliveryCenter);
	}

	@Override
	@Transactional
	public DeliveryCenter update(DeliveryCenter deliveryCenter) {
		Assert.notNull(deliveryCenter, "[Assertion failed] - deliveryCenter is required; it must not be null");

		DeliveryCenter pDeliveryCenter = super.update(deliveryCenter);
		if (BooleanUtils.isTrue(pDeliveryCenter.getIsDefault())) {
			deliveryCenterDao.clearDefault(pDeliveryCenter);
		}
		return pDeliveryCenter;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<DeliveryCenter> findPage(Store store, Pageable pageable) {
		return deliveryCenterDao.findPage(store, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DeliveryCenter> findAll(Store store) {
		return deliveryCenterDao.findAll(store);
	}

}