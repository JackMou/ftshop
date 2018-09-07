/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: FaOuRNBXOhZiKt2Lfp8AHamPqL4vaeCH
 */
package com.futengwl.audit;

/**
 * Audit - 审计者Provider
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface AuditorProvider<T> {

	/**
	 * 获取当前审计者
	 * 
	 * @return 当前审计者
	 */
	T getCurrentAuditor();

}