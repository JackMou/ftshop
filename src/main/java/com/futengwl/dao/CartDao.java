/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: erVV6xsNRR8nHqCTl8WoP7O0JYK+cbO1
 */
package com.futengwl.dao;

import com.futengwl.entity.Cart;

/**
 * Dao - 购物车
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface CartDao extends BaseDao<Cart, Long> {

	/**
	 * 删除过期购物车
	 */
	void deleteExpired();

}