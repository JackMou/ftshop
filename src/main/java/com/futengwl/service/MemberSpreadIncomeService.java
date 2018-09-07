package com.futengwl.service;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.MemberSpreadIncome;
import com.futengwl.entity.WithdrawRecord;

import java.math.BigDecimal;

/**
 * @title: MemberSpreadIncomeService
 * @package: com.futengwl.service
 * @description:
 * @author: hanbin
 * @date: 2018-06-04  15:36
 */
public interface MemberSpreadIncomeService  extends BaseService<MemberSpreadIncome, Long>{

    Page<MemberSpreadIncome> findAllByRecommenderId(Long recommenderId,String beginTime, String endTime, Pageable pageable);
    BigDecimal sumMoney(Long recommenderId, Integer status) ;
    BigDecimal sumMoneyWithRecommenderId( Integer status,String beginTime,String endTime) ;

    Page<WithdrawRecord> withdrawRecord(String beginTime, String endTime, Pageable pageable);
}
