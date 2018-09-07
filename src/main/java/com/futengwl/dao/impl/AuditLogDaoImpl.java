/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: MNuGbDAQV9v5+pk3TLSBSA04ResI70k2
 */
package com.futengwl.dao.impl;

import org.springframework.stereotype.Repository;

import com.futengwl.dao.AuditLogDao;
import com.futengwl.entity.AuditLog;

/**
 * Dao - 审计日志
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class AuditLogDaoImpl extends BaseDaoImpl<AuditLog, Long> implements AuditLogDao {

	@Override
	public void removeAll() {
		String jpql = "delete from AuditLog auditLog";
		entityManager.createQuery(jpql).executeUpdate();
	}

}