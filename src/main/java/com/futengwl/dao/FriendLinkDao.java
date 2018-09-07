/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: JahYVZ5W7SA4TKiAreqQPdUMWe3qAlRa
 */
package com.futengwl.dao;

import java.util.List;

import com.futengwl.entity.FriendLink;

/**
 * Dao - 友情链接
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface FriendLinkDao extends BaseDao<FriendLink, Long> {

	/**
	 * 查找友情链接
	 * 
	 * @param type
	 *            类型
	 * @return 友情链接
	 */
	List<FriendLink> findList(FriendLink.Type type);

}