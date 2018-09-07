/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: LC9wIO5p0Ws80RC4pKVgCN5T58HV8uP0
 */
package com.futengwl.service;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.MessageGroup;
import com.futengwl.entity.User;

/**
 * Service - 消息组
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface MessageGroupService extends BaseService<MessageGroup, Long> {

	/**
	 * 查找分页
	 * 
	 * @param user
	 *            用户
	 * @param pageable
	 *            分页信息
	 * @return 消息组分页
	 */
	Page<MessageGroup> findPage(User user, Pageable pageable);

	/**
	 * 查找
	 * 
	 * @param user1
	 *            用户1
	 * @param user2
	 *            用户2
	 * @return 消息组
	 */
	MessageGroup find(User user1, User user2);

	/**
	 * 删除
	 * 
	 * @param messageGroup
	 *            消息组
	 * @param user
	 *            用户
	 */
	void delete(MessageGroup messageGroup, User user);

}