/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: twx5lzDqP0J5H0X6x7CY1yWAv0mi45tV
 */
package com.futengwl.service;

import com.futengwl.entity.AftersalesSetting;
import com.futengwl.entity.Store;

/**
 * Service - 售后设置
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface AftersalesSettingService extends BaseService<AftersalesSetting, Long> {

	/**
	 * 通过店铺查找售后设置
	 * 
	 * @param store
	 *            店铺
	 * @return 售后设置
	 */
	AftersalesSetting findByStore(Store store);

}