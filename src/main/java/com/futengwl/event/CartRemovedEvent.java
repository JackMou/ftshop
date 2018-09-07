/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: ZFTpRY0D6YdhqnZsV58GPbrBcQpvzBgM
 */
package com.futengwl.event;

import com.futengwl.entity.Cart;
import com.futengwl.entity.Sku;

/**
 * Event - 移除购物车SKU
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public class CartRemovedEvent extends CartEvent {

	private static final long serialVersionUID = 6638396637072338544L;

	/**
	 * SKU
	 */
	private Sku sku;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param cart
	 *            购物车
	 * @param sku
	 *            SKU
	 */
	public CartRemovedEvent(Object source, Cart cart, Sku sku) {
		super(source, cart);
		this.sku = sku;
	}

	/**
	 * 获取SKU
	 * 
	 * @return SKU
	 */
	public Sku getSku() {
		return sku;
	}

}