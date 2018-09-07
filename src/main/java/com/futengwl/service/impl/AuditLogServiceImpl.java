/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: QIWPRXaxIePwmK4IFGput1YW3EFCNQfL
 */
package com.futengwl.service.impl;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.futengwl.dao.AuditLogDao;
import com.futengwl.entity.AuditLog;
import com.futengwl.service.AuditLogService;

/**
 * Service - 审计日志
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class AuditLogServiceImpl extends BaseServiceImpl<AuditLog, Long> implements AuditLogService {

	@Inject
	private AuditLogDao auditLogDao;

	@Override
	@Async
	public void create(AuditLog auditLog) {
		auditLogDao.persist(auditLog);
	}

	@Override
	public void clear() {
		auditLogDao.removeAll();
	}

}