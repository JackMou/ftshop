/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 5FwwN0vfKMoq0YJMj78Uv7FJTQML1DHj
 */
package com.futengwl.dao;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Business;
import com.futengwl.entity.BusinessCash;

/**
 * Dao - 商家提现
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface BusinessCashDao extends BaseDao<BusinessCash, Long> {

	/**
	 * 查找商家提现记录分页
	 * 
	 * @param status
	 *            状态
	 * @param bank
	 *            收款银行
	 * @param account
	 *            收款账号
	 * @param business
	 *            商家
	 * @param pageable
	 *            分页信息
	 * @return 商家提现记录分页
	 */
	Page<BusinessCash> findPage(BusinessCash.Status status, String bank, String account, Business business, Pageable pageable);

	/**
	 * 查找商家提现数量
	 * 
	 * @param status
	 *            状态
	 * @param bank
	 *            收款银行
	 * @param account
	 *            收款账号
	 * @param business
	 *            商家
	 * @return 商家提现数量
	 */
	Long count(BusinessCash.Status status, String bank, String account, Business business);

}