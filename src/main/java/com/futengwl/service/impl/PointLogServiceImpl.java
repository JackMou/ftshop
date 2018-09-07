/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: yInkB9tn8XPd9dxeQfQJT/T1tcYaFdMi
 */
package com.futengwl.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.PointLogDao;
import com.futengwl.entity.Member;
import com.futengwl.entity.PointLog;
import com.futengwl.service.PointLogService;

/**
 * Service - 积分记录
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class PointLogServiceImpl extends BaseServiceImpl<PointLog, Long> implements PointLogService {

	@Inject
	private PointLogDao pointLogDao;

	@Override
	@Transactional(readOnly = true)
	public Page<PointLog> findPage(Member member, Pageable pageable) {
		return pointLogDao.findPage(member, pageable);
	}

}