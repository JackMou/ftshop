/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 1YJCvqjNeDZOqVG3ktUBZlQGYoUALmhT
 */
package com.futengwl.event;

import com.futengwl.entity.User;

/**
 * Event - 用户登录
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public class UserLoggedInEvent extends UserEvent {

	private static final long serialVersionUID = 3087635924598684802L;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param user
	 *            用户
	 */
	public UserLoggedInEvent(Object source, User user) {
		super(source, user);
	}

}