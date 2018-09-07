/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 1Hem6J3YO6j5PZNJ23z4lf951x8DxFV4
 */
package com.futengwl.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Entity - 地区运费配置
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Entity
public class AreaFreightConfig extends FreightConfig {

	private static final long serialVersionUID = -1648340171356447281L;

	/**
	 * 地区
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Area area;

	/**
	 * 获取地区
	 * 
	 * @return 地区
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * 设置地区
	 * 
	 * @param area
	 *            地区
	 */
	public void setArea(Area area) {
		this.area = area;
	}

}