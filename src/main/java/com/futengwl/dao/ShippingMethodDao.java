/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: wYpNiGdMctnvwiV5gO3mz1f6yevwpSV9
 */
package com.futengwl.dao;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.ShippingMethod;

/**
 * Dao - 配送方式
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface ShippingMethodDao extends BaseDao<ShippingMethod, Long> {

	/**
	 * 查找配送方式分页
	 * 
	 * @param pageable
	 *            分页
	 * @return 配送方式分页
	 */
	Page<ShippingMethod> findPage(Pageable pageable);
}