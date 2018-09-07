/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: WUR67LORLYWqBR894HVP/H0DH31AARzS
 */
package com.futengwl.service.impl;

import com.futengwl.dao.ActiveMQProducerDao;
import com.futengwl.dao.AdminDao;
import com.futengwl.entity.ActiveMQProducer;
import com.futengwl.entity.Admin;
import com.futengwl.entity.Role;
import com.futengwl.entity.User;
import com.futengwl.service.ActiveMQProducerService;
import com.futengwl.service.AdminService;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

/**
 * Service - 管理员
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class ActiveMQProducerServiceImpl extends BaseServiceImpl<ActiveMQProducer, Long> implements ActiveMQProducerService {

	@Inject
	private ActiveMQProducerDao activeMQProducerDao;

	@Override
	public ActiveMQProducer getByOrderSn(String orderSn) {
		return activeMQProducerDao.find("orderSn",orderSn);
	}
}