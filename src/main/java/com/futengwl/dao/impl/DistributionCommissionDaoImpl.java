/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: rQVMxii7OtV+JO50hcDlDx+yhzFgQwdC
 */
package com.futengwl.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.DistributionCommissionDao;
import com.futengwl.entity.DistributionCommission;
import com.futengwl.entity.Distributor;

/**
 * Dao - 分销佣金
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class DistributionCommissionDaoImpl extends BaseDaoImpl<DistributionCommission, Long> implements DistributionCommissionDao {

	@Override
	public Page<DistributionCommission> findPage(Distributor distributor, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DistributionCommission> criteriaQuery = criteriaBuilder.createQuery(DistributionCommission.class);
		Root<DistributionCommission> root = criteriaQuery.from(DistributionCommission.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (distributor != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("distributor"), distributor));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

}