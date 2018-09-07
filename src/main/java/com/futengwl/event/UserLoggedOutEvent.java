/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 7UmH/4KgUJ/Ai+4RX+xXfvz4kZcticMO
 */
package com.futengwl.event;

import com.futengwl.entity.User;

/**
 * Event - 用户注销
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public class UserLoggedOutEvent extends UserEvent {

	private static final long serialVersionUID = 8560275705072178478L;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param user
	 *            用户
	 */
	public UserLoggedOutEvent(Object source, User user) {
		super(source, user);
	}

}