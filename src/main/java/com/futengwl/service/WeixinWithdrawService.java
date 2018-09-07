/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: LjHsBLmlADN2oPovOS3m7uPu2qhBY8ww
 */
package com.futengwl.service;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.UserAccount;
import com.futengwl.entity.WithdrawRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Service - 微信提现
 *
 * @author FTSHOP Team
 * @version 6.0
 */
public interface WeixinWithdrawService extends BaseService<WithdrawRecord, Long> {

    /**
     * 微信提现
     *
     * @param map
     * @return
     */
    Map doWithdraw(Map<String, String> map) throws Exception;

    /**
     * 获取提现记录
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<WithdrawRecord> queryWithdrawRecordList(Map map) throws Exception;

    Page<WithdrawRecord> withdrawRecord(String beginTime, String endTime, Pageable pageable);
    BigDecimal sumWithdrawRecord(String beginTime, String endTime);

}