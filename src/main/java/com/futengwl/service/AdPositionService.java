/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 2aSFtZI+rj1zS2z2JkUNxiiiUGHCzPB+
 */
package com.futengwl.service;

import com.futengwl.entity.AdPosition;

/**
 * Service - 广告位
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface AdPositionService extends BaseService<AdPosition, Long> {

	/**
	 * 查找广告位
	 * 
	 * @param id
	 *            ID
	 * @param useCache
	 *            是否使用缓存
	 * @return 广告位
	 */
	AdPosition find(Long id, boolean useCache);

}