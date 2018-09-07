/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: VHVvFfyr9Pg7pLDtOKzXWz6iIX8PjHd4
 */
package com.futengwl.event;

import org.springframework.context.ApplicationEvent;

import com.futengwl.entity.Cart;

/**
 * Event - 购物车
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public abstract class CartEvent extends ApplicationEvent {

	private static final long serialVersionUID = -4180050946434009635L;

	/**
	 * 购物车
	 */
	private Cart cart;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param cart
	 *            购物车
	 */
	public CartEvent(Object source, Cart cart) {
		super(source);
		this.cart = cart;
	}

	/**
	 * 获取购物车
	 * 
	 * @return 购物车
	 */
	public Cart getCart() {
		return cart;
	}

}