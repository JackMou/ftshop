/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: t3J7nj54oU9fLXIuQHAyrq64d0EDyBRq
 */
package com.futengwl.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.futengwl.entity.SitemapIndex;
import com.futengwl.entity.SitemapUrl;
import com.futengwl.service.SitemapIndexService;
import com.futengwl.service.SitemapUrlService;

/**
 * Service - Sitemap索引
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class SitemapIndexServiceImpl implements SitemapIndexService {

	@Inject
	private SitemapUrlService sitemapUrlService;

	@Override
	@Transactional(readOnly = true)
	public List<SitemapIndex> generate(SitemapUrl.Type type, int maxSitemapUrlSize) {
		Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");
		Assert.state(maxSitemapUrlSize >= 0, "[Assertion failed] - maxSitemapUrlSize must be equal or greater than 0");

		List<SitemapIndex> sitemapIndexs = new ArrayList<>();
		Long sitemapUrlSize = sitemapUrlService.count(type);
		for (int i = 0; i < Math.ceil((double) sitemapUrlSize / (double) maxSitemapUrlSize); i++) {
			SitemapIndex sitemapIndex = new SitemapIndex();
			sitemapIndex.setType(type);
			sitemapIndex.setIndex(i);
			sitemapIndexs.add(sitemapIndex);
		}
		return sitemapIndexs;
	}

}