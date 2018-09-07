/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: ZGqMvgWpndoNe3fjZs5ijSA1rdKrqRBI
 */
package com.futengwl.service;

import com.futengwl.entity.StoreCategory;

/**
 * Service - 店铺分类
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface StoreCategoryService extends BaseService<StoreCategory, Long> {

	/**
	 * 判断名称是否存在
	 * 
	 * @param name
	 *            名称(忽略大小写)
	 * @return 名称是否存在
	 */
	boolean nameExists(String name);

}