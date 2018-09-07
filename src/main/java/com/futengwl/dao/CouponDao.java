/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: ZHZG9ErINI7kwbPpzd7PjUsMp3hFHPLL
 */
package com.futengwl.dao;

import java.util.List;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Coupon;
import com.futengwl.entity.Store;

/**
 * Dao - 优惠券
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface CouponDao extends BaseDao<Coupon, Long> {

	/**
	 * 查找优惠券
	 * 
	 * @param store
	 *            店铺
	 * @param isEnabled
	 *            是否启用
	 * @param isExchange
	 *            是否允许积分兑换
	 * @param hasExpired
	 *            是否已过期
	 * @return 优惠券
	 */
	List<Coupon> findList(Store store, Boolean isEnabled, Boolean isExchange, Boolean hasExpired);

	/**
	 * 查找优惠券分页
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @param isExchange
	 *            是否允许积分兑换
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 优惠券分页
	 */
	Page<Coupon> findPage(Boolean isEnabled, Boolean isExchange, Boolean hasExpired, Pageable pageable);

	/**
	 * 查找优惠券分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页信息
	 * @return 优惠券分页
	 */
	Page<Coupon> findPage(Store store, Pageable pageable);

}