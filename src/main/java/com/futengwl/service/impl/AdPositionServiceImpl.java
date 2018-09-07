/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: nXiM2upDjBKuopPL43kmqgS1gge15sjM
 */
package com.futengwl.service.impl;

import javax.inject.Inject;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futengwl.dao.AdPositionDao;
import com.futengwl.entity.AdPosition;
import com.futengwl.service.AdPositionService;

/**
 * Service - 广告位
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class AdPositionServiceImpl extends BaseServiceImpl<AdPosition, Long> implements AdPositionService {

	@Inject
	private AdPositionDao adPositionDao;

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "adPosition", condition = "#useCache")
	public AdPosition find(Long id, boolean useCache) {
		return adPositionDao.find(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public AdPosition save(AdPosition adPosition) {
		return super.save(adPosition);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public AdPosition update(AdPosition adPosition) {
		return super.update(adPosition);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public AdPosition update(AdPosition adPosition, String... ignoreProperties) {
		return super.update(adPosition, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public void delete(AdPosition adPosition) {
		super.delete(adPosition);
	}

}