/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: eKKbavtDxF2LfJ59lVhSSrT8vf74rXuR
 */
package com.futengwl.service;

import java.util.List;

import com.futengwl.entity.ParameterValue;

/**
 * Service - 参数值
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface ParameterValueService {

	/**
	 * 参数值过滤
	 * 
	 * @param parameterValues
	 *            参数值
	 */
	void filter(List<ParameterValue> parameterValues);

}