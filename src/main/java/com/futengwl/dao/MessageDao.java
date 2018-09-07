/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: K7YyFqMmee8W2UsUCw8eflCIELiPMxvs
 */
package com.futengwl.dao;

import java.util.List;

import com.futengwl.entity.Message;
import com.futengwl.entity.MessageGroup;
import com.futengwl.entity.User;

/**
 * Dao - 消息
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface MessageDao extends BaseDao<Message, Long> {

	/**
	 * 查找
	 * 
	 * @param messageGroup
	 *            消息组
	 * @param user
	 *            用户
	 * @return 消息
	 */
	List<Message> findList(MessageGroup messageGroup, User user);

	/**
	 * 未读消息数量
	 * 
	 * @param messageGroup
	 *            消息组
	 * @param user
	 *            用户
	 * @return 未读消息数量
	 */
	Long unreadMessageCount(MessageGroup messageGroup, User user);

}