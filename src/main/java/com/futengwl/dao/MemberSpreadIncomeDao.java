/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: erVV6xsNRR8nHqCTl8WoP7O0JYK+cbO1
 */
package com.futengwl.dao;

import com.futengwl.entity.MemberSpreadIncome;
import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.WithdrawRecord;

import java.math.BigDecimal;


/**
 * @title: MemberSpreadIncomeDao
 * @package: com.futengwl.dao
 * @description:
 * @author: hanbin
 * @date: 2018-06-04  10:09
 */
public interface MemberSpreadIncomeDao extends BaseDao<MemberSpreadIncome, Long> {


    Page<MemberSpreadIncome> findAllByRecommenderId(Long recommenderId, String beginTime, String endTime, Pageable pageable);

    BigDecimal sumMoney(Long recommenderId, Integer status);

    BigDecimal sumMoneyWithRecommenderId(Integer status, String beginTime, String endTime);


}

