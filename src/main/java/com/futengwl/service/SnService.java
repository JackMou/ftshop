/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 0ly6qljXbhKqN7X6mYDrqDtN85O0TgPO
 */
package com.futengwl.service;

import com.futengwl.entity.Sn;

/**
 * Service - 序列号
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface SnService {

	/**
	 * 生成序列号
	 * 
	 * @param type
	 *            类型
	 * @return 序列号
	 */
	String generate(Sn.Type type);

}