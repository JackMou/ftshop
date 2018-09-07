/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: yp/R9pqI+hBFBXM7V4GUk0GLlAMLj1m6
 */
package com.futengwl.entity;

import java.io.Serializable;

import com.futengwl.Setting;
import com.futengwl.util.SystemUtils;

/**
 * Entity - Sitemap索引
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public class SitemapIndex implements Serializable {

	private static final long serialVersionUID = 4238589433765772175L;

	/**
	 * 链接地址
	 */
	private static final String LOC = "%s/sitemap/%s/%d.xml";

	/**
	 * 类型
	 */
	private SitemapUrl.Type type;

	/**
	 * 索引
	 */
	private Integer index;

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	public SitemapUrl.Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(SitemapUrl.Type type) {
		this.type = type;
	}

	/**
	 * 获取索引
	 * 
	 * @return 索引
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * 设置索引
	 * 
	 * @param index
	 *            索引
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * 获取链接地址
	 * 
	 * @return 链接地址
	 */
	public String getLoc() {
		Setting setting = SystemUtils.getSetting();
		return String.format(SitemapIndex.LOC, setting.getSiteUrl(), getType(), getIndex());
	}

}