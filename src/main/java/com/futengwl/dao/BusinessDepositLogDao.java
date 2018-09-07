/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: q2oj2P07IPEE8W1DiSjQkqQfUyCtYUXZ
 */
package com.futengwl.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Business;
import com.futengwl.entity.BusinessDepositLog;

/**
 * Dao - 商家预存款记录
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface BusinessDepositLogDao extends BaseDao<BusinessDepositLog, Long> {

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

	/**
	 * 查询提现总额
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 提现总额
	 */
	BigDecimal cashTotalAmount(Date beginDate, Date endDate);

}