/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: uQHLQ95TV3zPAa+58PcH3CDWmdvmY0IW
 */
package com.futengwl.dao;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Member;
import com.futengwl.entity.PointLog;

/**
 * Dao - 积分记录
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface PointLogDao extends BaseDao<PointLog, Long> {

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