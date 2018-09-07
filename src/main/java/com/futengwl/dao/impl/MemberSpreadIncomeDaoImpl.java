/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: OVfroheBIcI8Vp6sjAGf8F9/1rg0hpvL
 */
package com.futengwl.dao.impl;


/**
 * Dao - 购物车
 *
 * @author FTSHOP Team
 * @version 6.0
 */


import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.MemberSpreadIncomeDao;
import com.futengwl.entity.MemberSpreadIncome;
import com.futengwl.entity.WithdrawRecord;
import com.futengwl.util.DateUtils;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
 * CriteriaQuery<MemberSpreadIncome> criteriaQuery = criteriaBuilder.createQuery(MemberSpreadIncome.class);
 * Root<MemberSpreadIncome> root = criteriaQuery.from(MemberSpreadIncome.class);
 * criteriaQuery.select(root);
 * criteriaQuery.where(criteriaBuilder.equal(root.get("recommenderId"), recommenderId));
 * return super.findList(criteriaQuery);
 *
 * @title: MemberSpreadIncomeDaoImpl
 * @package: com.futengwl.dao.impl
 * @description:
 * @author: hanbin
 * @date: 2018-06-04  10:12
 */
@Repository
public class MemberSpreadIncomeDaoImpl extends BaseDaoImpl<MemberSpreadIncome, Long> implements MemberSpreadIncomeDao {
    @Override
    @Transactional(readOnly = true)
    public Page<MemberSpreadIncome> findAllByRecommenderId(Long recommenderId, String beginTime, String endTime, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MemberSpreadIncome> criteriaQuery = criteriaBuilder.createQuery(MemberSpreadIncome.class);
        Root<MemberSpreadIncome> root = criteriaQuery.from(MemberSpreadIncome.class);
        List<Predicate> predicateList = new ArrayList<>();
        if (!StringUtils.isEmpty(beginTime) && !StringUtils.isEmpty(endTime)) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate").as(Date.class), DateUtils.str2Date(beginTime, "yyyy-MM-dd hh:mm:ss")));
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdDate").as(Date.class), DateUtils.str2Date(endTime, "yyyy-MM-dd hh:mm:ss")));
        }
        if (null != recommenderId) {
            predicateList.add(criteriaBuilder.equal(root.get("recommenderId"), recommenderId));
        }
        if (predicateList.size() > 0) {
            Predicate[] predicates = new Predicate[predicateList.size()];
            criteriaQuery.where(predicateList.toArray(predicates));
        }
        criteriaQuery.select(root);
        return super.findPage(criteriaQuery, pageable);
    }

    @Override
    public BigDecimal sumMoney(Long recommenderId, Integer status) {
        final Query sumQuery = entityManager
                .createQuery("SELECT SUM(memberSpreadIncome.feeAmount) FROM MemberSpreadIncome memberSpreadIncome" +//
                        " WHERE memberSpreadIncome.recommenderId=:recommenderId " +
                        "AND (memberSpreadIncome.status=:status " +
                        "OR memberSpreadIncome.status is null )");
        sumQuery.setParameter("recommenderId", recommenderId)
                .setParameter("status", status);
        final Object result = sumQuery.getSingleResult();
        return (BigDecimal) result;
    }

    @Override
    public BigDecimal sumMoneyWithRecommenderId(Integer status, String beginTime, String endTime) {
        String query = "SELECT SUM(memberSpreadIncome.feeAmount) FROM MemberSpreadIncome memberSpreadIncome" +//
                " WHERE  memberSpreadIncome.status=:status ";
        if (!StringUtils.isEmpty(beginTime) && !StringUtils.isEmpty(endTime)) {
            query = query + "and memberSpreadIncome.createdDate BETWEEN :beginTime AND :endTime";
        }
        final Query sumQuery = entityManager
                .createQuery(query);
        sumQuery.setParameter("status", status);

        if (!StringUtils.isEmpty(beginTime) && !StringUtils.isEmpty(endTime)) {
            sumQuery.setParameter("beginTime", DateUtils.str2Date(beginTime, "yyyy-MM-dd hh:mm:ss"), TemporalType.DATE);
            sumQuery.setParameter("endTime", DateUtils.str2Date(endTime, "yyyy-MM-dd hh:mm:ss"), TemporalType.DATE);
        }
        final Object result = sumQuery.getSingleResult();
        return (BigDecimal) result;
    }




}

