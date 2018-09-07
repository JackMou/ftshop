/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: wmQ/h2S8tNHVEOtSt3MJAMEPbfn1itfE
 */
package com.futengwl.service;

import java.util.List;

import com.futengwl.entity.SpecificationItem;
import com.futengwl.entity.SpecificationValue;

/**
 * Service - 规格值
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface SpecificationValueService {

	/**
	 * 规格值验证
	 * 
	 * @param specificationItems
	 *            规格项
	 * @param specificationValues
	 *            规格值
	 * @return 验证结果
	 */
	boolean isValid(List<SpecificationItem> specificationItems, List<SpecificationValue> specificationValues);

}