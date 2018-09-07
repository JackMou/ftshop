/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: g0naHEGejIWUo9Gn0sc6G/3Ms8JBZXsD
 */
package com.futengwl.event;

import com.futengwl.entity.Cart;

/**
 * Event - 合并购物车
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public class CartMergedEvent extends CartEvent {

	private static final long serialVersionUID = -320699877093325080L;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param cart
	 *            购物车
	 */
	public CartMergedEvent(Object source, Cart cart) {
		super(source, cart);
	}

}