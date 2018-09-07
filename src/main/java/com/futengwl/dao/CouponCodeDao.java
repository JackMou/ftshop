/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 6Lyo10FOeA/ZjH3bajE9rfk10FNHS/uE
 */
package com.futengwl.dao;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Coupon;
import com.futengwl.entity.CouponCode;
import com.futengwl.entity.Member;

/**
 * Dao - 优惠码
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface CouponCodeDao extends BaseDao<CouponCode, Long> {

	/**
	 * 查找优惠码分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 优惠码分页
	 */
	Page<CouponCode> findPage(Member member, Pageable pageable);

	/**
	 * 查找优惠码数量
	 * 
	 * @param coupon
	 *            优惠券
	 * @param member
	 *            会员
	 * @param hasBegun
	 *            是否已开始
	 * @param hasExpired
	 *            是否已过期
	 * @param isUsed
	 *            是否已使用
	 * @return 优惠码数量
	 */
	Long count(Coupon coupon, Member member, Boolean hasBegun, Boolean hasExpired, Boolean isUsed);

}