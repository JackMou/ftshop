/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: Zovz93Tgb2hmkvMmpqe/Av9l6P57XtpZ
 */
package com.futengwl.service;

import java.util.List;

import com.futengwl.entity.Message;
import com.futengwl.entity.MessageGroup;
import com.futengwl.entity.User;

/**
 * Service - 消息
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface MessageService extends BaseService<Message, Long> {

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

	/**
	 * 查阅
	 * 
	 * @param messageGroup
	 *            消息组
	 * @param currentUser
	 *            当前用户
	 */
	void consult(MessageGroup messageGroup, User currentUser);

	/**
	 * 发送
	 * 
	 * @param type
	 *            用户类型
	 * @param fromUser
	 *            发送人
	 * @param toUser
	 *            接收人
	 * @param content
	 *            内容
	 * @param ip
	 *            ip
	 */
	void send(User.Type type, User fromUser, User toUser, String content, String ip);

	/**
	 * 回复
	 * 
	 * @param messageGroup
	 *            消息组
	 * @param fromUser
	 *            发送人
	 * @param content
	 *            内容
	 * @param ip
	 *            ip
	 */
	void reply(MessageGroup messageGroup, User fromUser, String content, String ip);

}