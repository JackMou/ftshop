/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: MCQQ/N6Y5BHNYmbNOgrPmfz4F+Ie5oOa
 */
package com.futengwl;

/**
 * 公共参数
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public final class CommonAttributes {

	/**
	 * 日期格式配比
	 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/**
	 * ftshop.xml文件路径
	 */
	public static final String FTSHOP_XML_PATH = "classpath:ftshop.xml";

	/**
	 * ftshop.properties文件路径
	 */
	public static final String FTSHOP_PROPERTIES_PATH = "classpath:ftshop.properties";

	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
	}

}