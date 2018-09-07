/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 5xSBWxd6QPoolQwaBMYImiOBKygb7r8l
 */
package com.futengwl.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.BusinessDepositLogDao;
import com.futengwl.entity.Business;
import com.futengwl.entity.BusinessDepositLog;
import com.futengwl.service.BusinessDepositLogService;

/**
 * Service - 商家预存款记录
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class BusinessDepositLogServiceImpl extends BaseServiceImpl<BusinessDepositLog, Long> implements BusinessDepositLogService {

	@Inject
	private BusinessDepositLogDao businessDepositLogDao;

	@Override
	@Transactional(readOnly = true)
	public Page<BusinessDepositLog> findPage(Business business, Pageable pageable) {
		return businessDepositLogDao.findPage(business, pageable);
	}

}