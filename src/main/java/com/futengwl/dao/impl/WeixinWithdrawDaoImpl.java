/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 5HFFlpiYIqZCi3gMqsoX18O44PQQNhFc
 */
package com.futengwl.dao.impl;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.WeixinWithdrawDao;
import com.futengwl.entity.MemberSpreadIncome;
import com.futengwl.entity.WithdrawRecord;
import com.futengwl.util.DateUtils;
import org.springframework.stereotype.Repository;
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
import java.util.Map;

/**
 * Dao - 用户提现
 *
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class WeixinWithdrawDaoImpl extends BaseDaoImpl<WithdrawRecord, Long> implements WeixinWithdrawDao {

    @Override
    public List<WithdrawRecord> queryWithdrawRecordList(Map map) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WithdrawRecord> criteriaQuery = criteriaBuilder.createQuery(WithdrawRecord.class);
        Root<WithdrawRecord> root = criteriaQuery.from(WithdrawRecord.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
        criteriaQuery.where(criteriaBuilder.equal(root.get("userId"), map.get("userId")));
        return super.findList(criteriaQuery, null, null);
    }

    @Override
    public Page<WithdrawRecord> withdrawRecord(String beginTime, String endTime, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WithdrawRecord> criteriaQuery = criteriaBuilder.createQuery(WithdrawRecord.class);
        Root<WithdrawRecord> root = criteriaQuery.from(WithdrawRecord.class);
        List<Predicate> predicateList = new ArrayList<>();
        if (!StringUtils.isEmpty(beginTime) && !StringUtils.isEmpty(endTime)) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate").as(Date.class), DateUtils.str2Date(beginTime, "yyyy-MM-dd hh:mm:ss")));
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdDate").as(Date.class), DateUtils.str2Date(endTime, "yyyy-MM-dd hh:mm:ss")));
        }
        if (predicateList.size() > 0) {
            Predicate[] predicates = new Predicate[predicateList.size()];
            criteriaQuery.where(predicateList.toArray(predicates));
        }
        criteriaQuery.select(root);
        return super.findPage(criteriaQuery, pageable);
    }

    @Override
    public BigDecimal sumWithdrawRecord(String beginTime, String endTime) {
        String query = "SELECT SUM(withdrawRecord.amout) FROM WithdrawRecord withdrawRecord" +//
                " WHERE  1=1";
        if (!StringUtils.isEmpty(beginTime) && !StringUtils.isEmpty(endTime)) {
            query = query + "and withdrawRecord.createdDate BETWEEN :beginTime AND :endTime";
        }
        final Query sumQuery = entityManager
                .createQuery(query);
        if (!StringUtils.isEmpty(beginTime) && !StringUtils.isEmpty(endTime)) {
            sumQuery.setParameter("beginTime", DateUtils.str2Date(beginTime, "yyyy-MM-dd hh:mm:ss"), TemporalType.DATE);
            sumQuery.setParameter("endTime", DateUtils.str2Date(endTime, "yyyy-MM-dd hh:mm:ss"), TemporalType.DATE);
        }
        final Object result = sumQuery.getSingleResult();
        return (BigDecimal) result;
    }


}