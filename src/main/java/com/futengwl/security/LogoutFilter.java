/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: mYvNsJxlOassQF38ksVK4OYCsETT4M0V
 */
package com.futengwl.security;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.ApplicationEventPublisher;

import com.futengwl.entity.User;
import com.futengwl.event.UserLoggedOutEvent;
import com.futengwl.service.UserService;

/**
 * Security - 注销过滤器
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {

	@Inject
	private ApplicationEventPublisher applicationEventPublisher;
	@Inject
	private UserService userService;

	/**
	 * 请求前处理
	 * 
	 * @param servletRequest
	 *            ServletRequest
	 * @param servletResponse
	 *            ServletResponse
	 * @return 是否继续执行
	 */
	@Override
	protected boolean preHandle(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		User currentUser = userService.getCurrent();
		applicationEventPublisher.publishEvent(new UserLoggedOutEvent(this, currentUser));

		return super.preHandle(servletRequest, servletResponse);
	}

}