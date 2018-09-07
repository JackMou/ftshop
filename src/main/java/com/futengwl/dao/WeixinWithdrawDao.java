/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: AhwYtJVikDjFqQok3nNc5OuzTS0gQgvq
 */
package com.futengwl.dao;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.WithdrawRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Dao - 用户提现
 *
 * @author FTSHOP Team
 * @version 6.0
 */
public interface WeixinWithdrawDao extends BaseDao<WithdrawRecord, Long> {

    List<WithdrawRecord> queryWithdrawRecordList(Map map);

    Page<WithdrawRecord> withdrawRecord(String beginTime, String endTime, Pageable pageable);

    BigDecimal sumWithdrawRecord(String beginTime, String endTime);

}