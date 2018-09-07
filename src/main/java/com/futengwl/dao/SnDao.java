/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: ih668FOzsrh1vna7/uPFFv8mQp7zIW/D
 */
package com.futengwl.dao;

import com.futengwl.entity.Sn;

/**
 * Dao - 序列号
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface SnDao {

	/**
	 * 生成序列号
	 * 
	 * @param type
	 *            类型
	 * @return 序列号
	 */
	String generate(Sn.Type type);

}