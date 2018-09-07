/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: RXU8MypcM1AuWeBCgLw/yUWJvSIShrbn
 */
package com.futengwl.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.MemberDepositLogDao;
import com.futengwl.entity.Member;
import com.futengwl.entity.MemberDepositLog;
import com.futengwl.service.MemberDepositLogService;

/**
 * Service - 会员预存款记录
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class MemberDepositLogServiceImpl extends BaseServiceImpl<MemberDepositLog, Long> implements MemberDepositLogService {

	@Inject
	private MemberDepositLogDao memberDepositLogDao;

	@Override
	@Transactional(readOnly = true)
	public Page<MemberDepositLog> findPage(Member member, Pageable pageable) {
		return memberDepositLogDao.findPage(member, pageable);
	}

}