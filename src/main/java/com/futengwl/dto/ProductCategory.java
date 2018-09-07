/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: +pzzZUXpKfpW6lwG4BmRqF/CkMcOgiBO
 */
package com.futengwl.dto;

import com.futengwl.entity.*;
import com.futengwl.entity.Parameter;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity - 商品分类
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Entity
public class ProductCategory {

	private static final long serialVersionUID = 5095521437302778876L;

	/**
	 * id
	 */
	private Long id;
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 树路径
	 */
	private String treePath;

	/**
	 * 层级
	 */
	private Integer grade;


	/**
	 * 获取名称
	 * 
	 * @return Id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置名称
	 * 
	 * @param id
	 *            Id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取名称
	 *
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 *
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取树路径
	 * 
	 * @return 树路径
	 */
	public String getTreePath() {
		return treePath;
	}

	/**
	 * 设置树路径
	 * 
	 * @param treePath
	 *            树路径
	 */
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	/**
	 * 获取层级
	 * 
	 * @return 层级
	 */
	public Integer getGrade() {
		return grade;
	}

	/**
	 * 设置层级
	 * 
	 * @param grade
	 *            层级
	 */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}

}