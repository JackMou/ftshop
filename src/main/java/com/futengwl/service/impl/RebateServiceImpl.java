package com.futengwl.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.RebateDao;
import com.futengwl.entity.Rebate;
import com.futengwl.service.RebateService;


@Service
public class RebateServiceImpl extends BaseServiceImpl<Rebate, Long> implements RebateService {

	@Inject
	private RebateDao rebateDao;
	
	@Override
	public Long checkIsBuy(String telephone) {
		return rebateDao.checkIsbuy(telephone);
		
	}

	@Override
	public List<Rebate> findRebate(Date beginDate, Date endDate, String mobile, String linkmanTelephone) {
		
		return rebateDao.findRebate(beginDate, endDate, mobile, linkmanTelephone);
	}

	@Override
	public boolean checkLinkman(String linkmanTelephone) {
		return rebateDao.exists("linkmanTelephone", linkmanTelephone);
	}

	@Override
	public Page<Rebate> findPage(Date beginDate, Date endDate, Pageable pageable) {
		
		return rebateDao.findPage(beginDate, endDate, pageable);
	}
}
