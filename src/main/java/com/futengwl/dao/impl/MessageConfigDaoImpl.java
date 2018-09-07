/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: R2gg8sTDFNwm356HuUM+tfEZant0AUT7
 */
package com.futengwl.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.futengwl.dao.MessageConfigDao;
import com.futengwl.entity.MessageConfig;

/**
 * Dao - 消息配置
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class MessageConfigDaoImpl extends BaseDaoImpl<MessageConfig, Long> implements MessageConfigDao {

	@Override
	public MessageConfig find(MessageConfig.Type type) {
		if (type == null) {
			return null;
		}
		try {
			String jpql = "select messageConfig from MessageConfig messageConfig where messageConfig.type = :type";
			return entityManager.createQuery(jpql, MessageConfig.class).setParameter("type", type).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}