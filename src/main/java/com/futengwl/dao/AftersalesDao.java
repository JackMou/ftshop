/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: NwEsstGmfkzHHSajQlq2IWkz4i4qcaeT
 */
package com.futengwl.dao;

import java.util.List;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Aftersales;
import com.futengwl.entity.Member;
import com.futengwl.entity.OrderItem;
import com.futengwl.entity.Store;

/**
 * Dao - 售后
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface AftersalesDao extends BaseDao<Aftersales, Long> {

	/**
	 * 查找售后列表
	 * 
	 * @param orderItems
	 *            订单项
	 * @return 售后列表
	 */
	List<Aftersales> findList(List<OrderItem> orderItems);

	/**
	 * 查找售后分页
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param member
	 *            会员
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页信息
	 * @return 售后分页
	 */
	Page<Aftersales> findPage(Aftersales.Type type, Aftersales.Status status, Member member, Store store, Pageable pageable);

}