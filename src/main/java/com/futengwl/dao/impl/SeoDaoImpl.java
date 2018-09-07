/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: bgNd+wmqYi7siHw2mUXUivDoNIaqAATt
 */
package com.futengwl.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.futengwl.dao.SeoDao;
import com.futengwl.entity.Seo;

/**
 * Dao - SEO设置
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class SeoDaoImpl extends BaseDaoImpl<Seo, Long> implements SeoDao {

	@Override
	public Seo find(Seo.Type type) {
		if (type == null) {
			return null;
		}
		try {
			String jpql = "select seo from Seo seo where seo.type = :type";
			return entityManager.createQuery(jpql, Seo.class).setParameter("type", type).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}