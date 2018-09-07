/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: /j1Y2tUSru92R2e/8OYOS4RPMZlwcdKO
 */
package com.futengwl.service;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Member;
import com.futengwl.entity.PointLog;

/**
 * Service - 积分记录
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface PointLogService extends BaseService<PointLog, Long> {

	/**
	 * 查找积分记录分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 积分记录分页
	 */
	Page<PointLog> findPage(Member member, Pageable pageable);

}