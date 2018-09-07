/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 1EehQxcF8xcJmOK4sTtrDeavieORQsuC
 */
package com.futengwl.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.DistributionCommissionDao;
import com.futengwl.entity.DistributionCommission;
import com.futengwl.entity.Distributor;
import com.futengwl.service.DistributionCommissionService;

/**
 * Service - 分销佣金
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class DistributionCommissionServiceImpl extends BaseServiceImpl<DistributionCommission, Long> implements DistributionCommissionService {

	@Inject
	private DistributionCommissionDao distributionCommissionDao;

	@Override
	@Transactional(readOnly = true)
	public Page<DistributionCommission> findPage(Distributor distributor, Pageable pageable) {
		return distributionCommissionDao.findPage(distributor, pageable);
	}

}