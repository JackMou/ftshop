/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: LiFAUDqkhnkdNi6Jj2/r/En9JREB81AE
 */
package com.futengwl.dao.impl;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.SocialUserDao;
import com.futengwl.entity.SocialUser;
import com.futengwl.entity.User;

/**
 * Dao - 社会化用户
 * 
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class SocialUserDaoImpl extends BaseDaoImpl<SocialUser, Long> implements SocialUserDao {

	@Override
	public SocialUser find(String loginPluginId, String uniqueId) {
		if (StringUtils.isEmpty(loginPluginId) || StringUtils.isEmpty(uniqueId)) {
			return null;
		}
		try {
			String jpql = "select socialUser from SocialUser socialUser where socialUser.loginPluginId = :loginPluginId and socialUser.uniqueId = :uniqueId";
			return entityManager.createQuery(jpql, SocialUser.class).setParameter("loginPluginId", loginPluginId).setParameter("uniqueId", uniqueId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Page<SocialUser> findPage(User user, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SocialUser> criteriaQuery = criteriaBuilder.createQuery(SocialUser.class);
		Root<SocialUser> root = criteriaQuery.from(SocialUser.class);
		criteriaQuery.select(root);
		if (user != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.get("user"), user));
		}
		return super.findPage(criteriaQuery, pageable);
	}

}