package com.futengwl.service.impl;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.MemberSpreadIncomeDao;
import com.futengwl.entity.MemberSpreadIncome;
import com.futengwl.entity.WithdrawRecord;
import com.futengwl.service.MemberSpreadIncomeService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

/**
 * @title: MemberSpreadIncomeServiceImpl
 * @package: com.futengwl.service.impl
 * @description:
 * @author: hanbin
 * @date: 2018-06-04  15:39
 */
@Service
public class MemberSpreadIncomeServiceImpl extends BaseServiceImpl<MemberSpreadIncome, Long> implements MemberSpreadIncomeService {

    @Inject
    private MemberSpreadIncomeDao MemberSpreadIncomeDao;

    @Override
    public Page<MemberSpreadIncome> findAllByRecommenderId(Long recommenderId,String beginTime, String endTime, Pageable pageable) {

        return MemberSpreadIncomeDao.findAllByRecommenderId(recommenderId,beginTime, endTime, pageable);
    }

    @Override
    public BigDecimal sumMoney(Long recommenderId, Integer status) {
        return MemberSpreadIncomeDao.sumMoney(recommenderId, status);
    }

    @Override
    public BigDecimal sumMoneyWithRecommenderId(Integer status,String beginTime, String endTime) {
        return MemberSpreadIncomeDao.sumMoneyWithRecommenderId(status,beginTime, endTime);
    }

    @Override
    public Page<WithdrawRecord> withdrawRecord(String beginTime, String endTime, Pageable pageable) {
        return null;
    }
}
