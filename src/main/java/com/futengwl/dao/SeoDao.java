/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: YC0yBZMrb9A9aCrYMluifVWxwpepsfuQ
 */
package com.futengwl.dao;

import com.futengwl.entity.Seo;

/**
 * Dao - SEO设置
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface SeoDao extends BaseDao<Seo, Long> {

	/**
	 * 查找SEO设置
	 * 
	 * @param type
	 *            类型
	 * @return SEO设置
	 */
	Seo find(Seo.Type type);

}