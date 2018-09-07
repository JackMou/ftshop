package com.futengwl.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.RebateDao;
import com.futengwl.entity.Rebate;

@Repository
public class RebateDaoImpl extends BaseDaoImpl<Rebate, Long> implements RebateDao {

	@Override
	public Long checkIsbuy(String mobile) {

		// CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		String jpql = "SELECT COUNT(*) FROM OrderItem i WHERE i.order.member.mobile=:mobile AND i.price=3980 AND i.order.status in (3,4,5) AND i.createdDate<'2018-07-15 23:59:59'";
		TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
		return query.setParameter("mobile", mobile).getSingleResult();

		// return query.getSingleResult().intValue();
	}

	@Override
	public List<Rebate> findRebate(Date beginDate, Date endDate, String mobile, String linkmanTelephone) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Rebate> criteriaQuery = criteriaBuilder.createQuery(Rebate.class);
		Root<Rebate> root = criteriaQuery.from(Rebate.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), endDate));
		}
		if (mobile != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mobile"), mobile));
		}
		if (linkmanTelephone != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("linkmanTelephone"), linkmanTelephone));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery);
	}

	@Override
	public Page<Rebate> findPage(Date beginDate, Date endDate, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Rebate> criteriaQuery = criteriaBuilder.createQuery(Rebate.class);
		Root<Rebate> root = criteriaQuery.from(Rebate.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

}