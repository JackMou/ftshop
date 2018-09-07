/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: F9ILEFPmu9MicefL9j1Y+DYC38GkXu7T
 */
package com.futengwl.service.impl;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.ConsultationDao;
import com.futengwl.dao.MemberDao;
import com.futengwl.dao.ProductDao;
import com.futengwl.entity.Consultation;
import com.futengwl.entity.Member;
import com.futengwl.entity.Product;
import com.futengwl.entity.Store;
import com.futengwl.service.ConsultationService;

/**
 * Service - 咨询
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class ConsultationServiceImpl extends BaseServiceImpl<Consultation, Long> implements ConsultationService {

	@Inject
	private ConsultationDao consultationDao;
	@Inject
	private MemberDao memberDao;
	@Inject
	private ProductDao productDao;

	@Override
	@Transactional(readOnly = true)
	public List<Consultation> findList(Member member, Product product, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders) {
		return consultationDao.findList(member, product, isShow, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "consultation", condition = "#useCache")
	public List<Consultation> findList(Long memberId, Long productId, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		Member member = memberDao.find(memberId);
		if (memberId != null && member == null) {
			return Collections.emptyList();
		}
		Product product = productDao.find(productId);
		if (productId != null && product == null) {
			return Collections.emptyList();
		}
		return consultationDao.findList(member, product, isShow, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Consultation> findPage(Member member, Product product, Store store, Boolean isShow, Pageable pageable) {
		return consultationDao.findPage(member, product, store, isShow, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(Member member, Product product, Boolean isShow) {
		return consultationDao.count(member, product, isShow);
	}

	@Override
	@CacheEvict(value = { "product", "consultation" }, allEntries = true)
	public void reply(Consultation consultation, Consultation replyConsultation) {
		if (consultation == null || replyConsultation == null) {
			return;
		}
		consultation.setIsShow(true);

		replyConsultation.setIsShow(true);
		replyConsultation.setProduct(consultation.getProduct());
		replyConsultation.setForConsultation(consultation);
		replyConsultation.setStore(consultation.getStore());
		consultationDao.persist(replyConsultation);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "consultation" }, allEntries = true)
	public Consultation save(Consultation consultation) {
		return super.save(consultation);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "consultation" }, allEntries = true)
	public Consultation update(Consultation consultation) {
		return super.update(consultation);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "consultation" }, allEntries = true)
	public Consultation update(Consultation consultation, String... ignoreProperties) {
		return super.update(consultation, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "consultation" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "consultation" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "consultation" }, allEntries = true)
	public void delete(Consultation consultation) {
		super.delete(consultation);
	}

}