/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: ++66cFzMkp3UkWFfv1mlPxmXtxxBgFS1
 */
package com.futengwl.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futengwl.dao.SnDao;
import com.futengwl.entity.Sn;
import com.futengwl.service.SnService;

/**
 * Service - 序列号
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class SnServiceImpl implements SnService {

	@Inject
	private SnDao snDao;

	@Override
	@Transactional
	public String generate(Sn.Type type) {
		return snDao.generate(type);
	}

}