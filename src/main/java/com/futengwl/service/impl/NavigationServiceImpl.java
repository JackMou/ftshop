/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: kHZPFRqGuO7q6q0Y4R0zn9fr0uRGgnDX
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
import com.futengwl.dao.NavigationDao;
import com.futengwl.dao.NavigationGroupDao;
import com.futengwl.entity.Navigation;
import com.futengwl.entity.NavigationGroup;
import com.futengwl.service.NavigationService;

/**
 * Service - 导航
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class NavigationServiceImpl extends BaseServiceImpl<Navigation, Long> implements NavigationService {

	@Inject
	private NavigationDao navigationDao;
	@Inject
	private NavigationGroupDao navigationGroupDao;

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "navigation", condition = "#useCache")
	public List<Navigation> findList(Long navigationGroupId, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		NavigationGroup navigationGroup = navigationGroupDao.find(navigationGroupId);
		if (navigationGroupId != null && navigationGroup == null) {
			return Collections.emptyList();
		}

		return navigationDao.findList(navigationGroup, count, filters, orders);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public Navigation save(Navigation navigation) {
		return super.save(navigation);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public Navigation update(Navigation navigation) {
		return super.update(navigation);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public Navigation update(Navigation navigation, String... ignoreProperties) {
		return super.update(navigation, ignoreProperties);
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
	public void delete(Navigation navigation) {
		super.delete(navigation);
	}

}