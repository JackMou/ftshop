/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: HeM1VX6b8Qz479W9GTIAAEgQ3Lx8hHrz
 */
package com.futengwl.service.impl;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.futengwl.dao.MemberRankDao;
import com.futengwl.entity.MemberRank;
import com.futengwl.service.MemberRankService;

/**
 * Service - 会员等级
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class MemberRankServiceImpl extends BaseServiceImpl<MemberRank, Long> implements MemberRankService {

	@Inject
	private MemberRankDao memberRankDao;

	@Override
	@Transactional(readOnly = true)
	public boolean amountExists(BigDecimal amount) {
		return memberRankDao.exists("amount", amount);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean amountUnique(Long id, BigDecimal amount) {
		return memberRankDao.unique(id, "amount", amount);
	}

	@Override
	@Transactional(readOnly = true)
	public MemberRank findDefault() {
		return memberRankDao.findDefault();
	}

	@Override
	@Transactional(readOnly = true)
	public MemberRank findByAmount(BigDecimal amount) {
		return memberRankDao.findByAmount(amount);
	}

	@Override
	@Transactional
	public MemberRank save(MemberRank memberRank) {
		Assert.notNull(memberRank, "[Assertion failed] - memberRank is required; it must not be null");

		if (BooleanUtils.isTrue(memberRank.getIsDefault())) {
			memberRankDao.clearDefault();
		}
		return super.save(memberRank);
	}

	@Override
	@Transactional
	public MemberRank update(MemberRank memberRank) {
		Assert.notNull(memberRank, "[Assertion failed] - memberRank is required; it must not be null");

		MemberRank pMemberRank = super.update(memberRank);
		if (BooleanUtils.isTrue(pMemberRank.getIsDefault())) {
			memberRankDao.clearDefault(pMemberRank);
		}
		return pMemberRank;
	}

}