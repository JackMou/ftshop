/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: lna272JR2YObwX5vLj/zIh+k0lGEV2NU
 */
package com.futengwl.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Audit - 审计注解
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface Audit {

	/**
	 * 动作
	 */
	String action();

}