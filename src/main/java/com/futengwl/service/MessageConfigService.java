/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 0UM4M4CwRWz1s4THkL1KmM6JjukomVbZ
 */
package com.futengwl.service;

import com.futengwl.entity.MessageConfig;

/**
 * Service - 消息配置
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface MessageConfigService extends BaseService<MessageConfig, Long> {

	/**
	 * 查找消息配置
	 * 
	 * @param type
	 *            类型
	 * @return 消息配置
	 */
	MessageConfig find(MessageConfig.Type type);

}