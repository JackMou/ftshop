/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 23ss4SpaUevT4AJvktjAqqB1W3fVy2Z4
 */
package com.futengwl.dao;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.SocialUser;
import com.futengwl.entity.User;

/**
 * Dao - 社会化用户
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface SocialUserDao extends BaseDao<SocialUser, Long> {

	/**
	 * 查找社会化用户
	 * 
	 * @param loginPluginId
	 *            登录插件ID
	 * @param uniqueId
	 *            唯一ID
	 * @return 社会化用户，若不存在则返回null
	 */
	SocialUser find(String loginPluginId, String uniqueId);

	/**
	 * 查找社会化用户分页
	 * 
	 * @param user
	 *            用户
	 * @param pageable
	 *            分页信息
	 * @return 社会化用户分页
	 */
	Page<SocialUser> findPage(User user, Pageable pageable);

}