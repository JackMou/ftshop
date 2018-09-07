/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: uVOPOMa4ek6PsfQVBTHii4h8IX164qqT
 */
package com.futengwl.dao;

import com.futengwl.entity.AuditLog;

/**
 * Dao - 审计日志
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface AuditLogDao extends BaseDao<AuditLog, Long> {

	/**
	 * 删除所有
	 */
	void removeAll();

}