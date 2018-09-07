/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: yjcG3lPkj43hIK8o41yfLDKDBTgOgA7W
 */
package com.futengwl.event;

import com.futengwl.entity.Cart;

/**
 * Event - 清空购物车SKU
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public class CartClearedEvent extends CartEvent {

	private static final long serialVersionUID = -5881246837387897341L;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param cart
	 *            购物车
	 */
	public CartClearedEvent(Object source, Cart cart) {
		super(source, cart);
	}

}