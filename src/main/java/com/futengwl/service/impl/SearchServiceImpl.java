/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 2xZ5uuMV61YVprgeukcXezRdWyiA0Tsh
 */
package com.futengwl.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.futengwl.service.SearchService;

/**
 * Service - 搜索
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class SearchServiceImpl implements SearchService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void index(Class<?> type) {
		index(type, true);
	}

	@Override
	public void index(Class<?> type, boolean purgeAll) {
		Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		fullTextEntityManager.createIndexer(type).purgeAllOnStart(purgeAll).start();
	}

}