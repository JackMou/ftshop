/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 6Q00HS6E2XrJU3u4ea02Nu0+fu3WvtOr
 */
package com.futengwl.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.DistributionCash;
import com.futengwl.entity.Distributor;

/**
 * Dao - 分销提现
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface DistributionCashDao extends BaseDao<DistributionCash, Long> {

	/**
	 * 查找分销提现记录分页
	 * 
	 * @param status
	 *            状态
	 * @param bank
	 *            收款银行
	 * @param account
	 *            收款账号
	 * @param accountHolder
	 *            开户人
	 * @param distributor
	 *            分销员
	 * @param pageable
	 *            分页信息
	 * @return 分销提现记录分页
	 */
	Page<DistributionCash> findPage(DistributionCash.Status status, String bank, String account, String accountHolder, Distributor distributor, Pageable pageable);

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

	/**
	 * 查找分销提现数量
	 * 
	 * @param status
	 *            状态
	 * @param bank
	 *            收款银行
	 * @param account
	 *            收款账号
	 * @param accountHolder
	 *            开户人
	 * @param distributor
	 *            分销员
	 * @return 分销提现数量
	 */
	Long count(DistributionCash.Status status, String bank, String account, String accountHolder, Distributor distributor);

}