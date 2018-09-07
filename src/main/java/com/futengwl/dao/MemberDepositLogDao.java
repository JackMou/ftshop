/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: yeZACnjw1PLlwm7Nt6r17Rmqrmr6072K
 */
package com.futengwl.dao;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Member;
import com.futengwl.entity.MemberDepositLog;

/**
 * Dao - 会员预存款记录
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface MemberDepositLogDao extends BaseDao<MemberDepositLog, Long> {

	/**
	 * 查找会员预存款记录分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 会员预存款记录分页
	 */
	Page<MemberDepositLog> findPage(Member member, Pageable pageable);

}