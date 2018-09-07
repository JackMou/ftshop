/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: ediTdggvdkHNduWEElTVf7QFn1heWSj3
 */
package com.futengwl.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.futengwl.dao.DistributorDao;
import com.futengwl.entity.Distributor;
import com.futengwl.entity.Member;
import com.futengwl.service.DistributorService;

/**
 * Service - 分销员
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class DistributorServiceImpl extends BaseServiceImpl<Distributor, Long> implements DistributorService {

	@Inject
	private DistributorDao distributorDao;

	@Override
	public void create(Member member) {
		Assert.notNull(member, "[Assertion failed] - member is required; it must not be null");

		Distributor distributor = new Distributor();
		distributor.setMember(member);
		distributor.setParent(null);
		distributor.setChildren(null);
		distributor.setDistributionCashs(null);
		distributor.setDistributionCommissions(null);
		distributorDao.persist(distributor);
	}

	@Override
	public void create(Member member, Member spreadMember) {
		Assert.notNull(member, "[Assertion failed] - member is required; it must not be null");
		Assert.notNull(spreadMember, "[Assertion failed] - spreadMember is required; it must not be null");
		Assert.state(!member.equals(spreadMember), "[Assertion failed] - member must not be spreadMember");

		Distributor spreadDistributor = spreadMember.getDistributor();

		if (spreadDistributor == null) {
			spreadDistributor = new Distributor();
			spreadDistributor.setMember(spreadMember);
			spreadDistributor.setParent(null);
			spreadDistributor.setChildren(null);
			spreadDistributor.setDistributionCashs(null);
			spreadDistributor.setDistributionCommissions(null);
			distributorDao.persist(spreadDistributor);
		}

		Distributor distributor = new Distributor();
		distributor.setMember(member);
		distributor.setParent(spreadDistributor);
		distributor.setChildren(null);
		distributor.setDistributionCashs(null);
		distributor.setDistributionCommissions(null);
		distributorDao.persist(distributor);

		Set<Distributor> childrens = spreadDistributor.getChildren();
		if (childrens == null) {
			childrens = new HashSet<>();
		}
		childrens.add(distributor);
		spreadDistributor.setChildren(childrens);
	}

	@Override
	public void modify(Distributor distributor, Member spreadMember) {
		Assert.notNull(distributor, "[Assertion failed] - distributor is required; it must not be null");
		Assert.state(!distributor.getMember().equals(spreadMember), "[Assertion failed] - distributor must not be spreadMember");

		if (spreadMember == null) {
			distributor.setParent(null);
		} else {
			distributor.setParent(spreadMember.getDistributor());
		}
	}

}