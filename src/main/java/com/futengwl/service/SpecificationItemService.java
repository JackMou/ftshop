/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: pzkUpSm3/OslW8B6yu76IaeduMxRAF29
 */
package com.futengwl.service;

import java.util.List;

import com.futengwl.entity.SpecificationItem;

/**
 * Service - 规格项
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface SpecificationItemService {

	/**
	 * 规格项过滤
	 * 
	 * @param specificationItems
	 *            规格项
	 */
	void filter(List<SpecificationItem> specificationItems);

}