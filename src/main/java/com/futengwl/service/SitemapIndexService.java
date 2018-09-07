/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: PmKy2uFMw1GajqbCxt6ULZMpOeF1p1YO
 */
package com.futengwl.service;

import java.util.List;

import com.futengwl.entity.SitemapIndex;
import com.futengwl.entity.SitemapUrl;

/**
 * Service - Sitemap索引
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface SitemapIndexService {

	/**
	 * 生成Sitemap索引
	 * 
	 * @param type
	 *            类型
	 * @param maxSitemapUrlSize
	 *            最大Sitemap URL数量
	 * @return Sitemap索引
	 */
	List<SitemapIndex> generate(SitemapUrl.Type type, int maxSitemapUrlSize);

}