/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: EmHp69kwJPvAi4huKM8KfFdAxvi9+/KI
 */
package com.futengwl.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futengwl.entity.NavigationGroup;
import com.futengwl.service.NavigationGroupService;

/**
 * Service - 导航组
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class NavigationGroupServiceImpl extends BaseServiceImpl<NavigationGroup, Long> implements NavigationGroupService {

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public NavigationGroup save(NavigationGroup navigationGroup) {
		return super.save(navigationGroup);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public NavigationGroup update(NavigationGroup navigationGroup) {
		return super.update(navigationGroup);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public NavigationGroup update(NavigationGroup navigationGroup, String... ignoreProperties) {
		return super.update(navigationGroup, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public void delete(NavigationGroup navigationGroup) {
		super.delete(navigationGroup);
	}

}