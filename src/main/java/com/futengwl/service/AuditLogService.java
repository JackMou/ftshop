/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: no3jHdsjPGgAfp0VgmD18NgYKXUnHG05
 */
package com.futengwl.service;

import com.futengwl.entity.AuditLog;

/**
 * Service - 审计日志
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface AuditLogService extends BaseService<AuditLog, Long> {

	/**
	 * 创建审计日志(异步)
	 * 
	 * @param auditLog
	 *            审计日志
	 */
	void create(AuditLog auditLog);

	/**
	 * 清空审计日志
	 */
	void clear();

}