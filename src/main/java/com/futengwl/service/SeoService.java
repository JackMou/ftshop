/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: nJEBjDddjtRiyKsXrRPyzutOzPJZ3stg
 */
package com.futengwl.service;

import com.futengwl.entity.Seo;

/**
 * Service - SEO设置
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface SeoService extends BaseService<Seo, Long> {

	/**
	 * 查找SEO设置
	 * 
	 * @param type
	 *            类型
	 * @return SEO设置
	 */
	Seo find(Seo.Type type);

	/**
	 * 查找SEO设置
	 * 
	 * @param type
	 *            类型
	 * @param useCache
	 *            是否使用缓存
	 * @return SEO设置
	 */
	Seo find(Seo.Type type, boolean useCache);

}