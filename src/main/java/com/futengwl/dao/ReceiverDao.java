/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: zDS7nKLO3SGzN8FI4X+cWFgv+ufQ1uAK
 */
package com.futengwl.dao;

import java.util.List;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Member;
import com.futengwl.entity.Receiver;

/**
 * Dao - 收货地址
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface ReceiverDao extends BaseDao<Receiver, Long> {

	/**
	 * 查找默认收货地址
	 * 
	 * @param member
	 *            会员
	 * @return 默认收货地址，若不存在则返回最新收货地址
	 */
	Receiver findDefault(Member member);

	/**
	 * 查找收货地址
	 * 
	 * @param member
	 *            会员
	 * @return 收货地址
	 */
	List<Receiver> findList(Member member);

	/**
	 * 查找收货地址分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 收货地址分页
	 */
	Page<Receiver> findPage(Member member, Pageable pageable);

	/**
	 * 清除默认
	 * 
	 * @param member
	 *            会员
	 */
	void clearDefault(Member member);

	/**
	 * 清除默认
	 * 
	 * @param member
	 *            会员
	 * @param exclude
	 *            排除收货地址
	 */
	void clearDefault(Member member, Receiver exclude);

}