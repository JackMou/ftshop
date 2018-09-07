/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: OVfroheBIcI8Vp6sjAGf8F9/1rg0hpvL
 */
package com.futengwl.dao.impl;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.futengwl.dao.CartDao;
import com.futengwl.entity.Cart;

/**
 * Dao - 购物车
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class CartDaoImpl extends BaseDaoImpl<Cart, Long> implements CartDao {

	@Override
	public void deleteExpired() {
		String cartItemJpql = "delete from CartItem cartItem where cartItem.cart.id in (select cart.id from Cart cart where cart.expire is not null and cart.expire <= :now)";
		String cartJpql = "delete from Cart cart where cart.expire is not null and cart.expire <= :now";
		Date now = new Date();
		entityManager.createQuery(cartItemJpql).setParameter("now", now).executeUpdate();
		entityManager.createQuery(cartJpql).setParameter("now", now).executeUpdate();
	}

}