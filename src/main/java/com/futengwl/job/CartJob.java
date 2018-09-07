/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 9QwBlYQpyQ93GwjJOlf8m9snZxdQQXV/
 */
package com.futengwl.job;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.futengwl.service.CartService;

/**
 * Job - 购物车
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Lazy(false)
@Component
public class CartJob {

	@Inject
	private CartService cartService;

	/**
	 * 删除过期购物车
	 */
	@Scheduled(cron = "${job.cart_delete_expired.cron}")
	public void deleteExpired() {
		cartService.deleteExpired();
	}

}