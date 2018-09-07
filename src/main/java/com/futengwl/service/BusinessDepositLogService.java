/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: CoJgHO+b4W+9bKGHar7T6xnzQNr3wvW6
 */
package com.futengwl.service;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Business;
import com.futengwl.entity.BusinessDepositLog;

/**
 * Service - 商家预存款记录
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface BusinessDepositLogService extends BaseService<BusinessDepositLog, Long> {

	/**
	 * 查找商家预存款记录分页
	 * 
	 * @param business
	 *            商家
	 * @param pageable
	 *            分页信息
	 * @return 商家预存款记录分页
	 */
	Page<BusinessDepositLog> findPage(Business business, Pageable pageable);

}