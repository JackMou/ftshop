/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: a095DKIq7U0g4IeV0JizIsJGxtdoab96
 */
package com.futengwl.dao;

import com.futengwl.entity.MessageConfig;

/**
 * Dao - 消息配置
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface MessageConfigDao extends BaseDao<MessageConfig, Long> {

	/**
	 * 查找消息配置
	 * 
	 * @param type
	 *            类型
	 * @return 消息配置
	 */
	MessageConfig find(MessageConfig.Type type);

}