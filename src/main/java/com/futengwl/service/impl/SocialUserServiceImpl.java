/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: Ikj6n3T8fp4zL8Wy7Nvd2AT0cLkYqZJE
 */
package com.futengwl.service.impl;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.SocialUserDao;
import com.futengwl.entity.SocialUser;
import com.futengwl.entity.User;
import com.futengwl.service.SocialUserService;

/**
 * Service - 社会化用户
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class SocialUserServiceImpl extends BaseServiceImpl<SocialUser, Long> implements SocialUserService {

	@Inject
	private SocialUserDao socialUserDao;

	@Override
	@Transactional(readOnly = true)
	public SocialUser find(String loginPluginId, String uniqueId) {
		return socialUserDao.find(loginPluginId, uniqueId);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SocialUser> findPage(User user, Pageable pageable) {
		return socialUserDao.findPage(user, pageable);
	}

	@Override
	public void bindUser(User user, SocialUser socialUser, String uniqueId) {
		Assert.notNull(socialUser, "[Assertion failed] - socialUser is required; it must not be null");
		Assert.hasText(uniqueId, "[Assertion failed] - uniqueId must have text; it must not be null, empty, or blank");

		if (!StringUtils.equals(socialUser.getUniqueId(), uniqueId) || socialUser.getUser() != null) {
			return;
		}

		socialUser.setUser(user);
	}

}