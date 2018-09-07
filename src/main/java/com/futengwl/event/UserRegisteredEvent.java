/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: WNtCWfG/RRFZdh3GSNTn0x8B6+MStP3l
 */
package com.futengwl.event;

import com.futengwl.entity.User;

/**
 * Event - 用户注册
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public class UserRegisteredEvent extends UserEvent {

	private static final long serialVersionUID = 3930447081075693577L;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param user
	 *            用户
	 */
	public UserRegisteredEvent(Object source, User user) {
		super(source, user);
	}

}