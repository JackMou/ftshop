/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: MIUWjnwZiMtO5oCf6ke7Gwfni9sF7Gun
 */
package com.futengwl.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import com.futengwl.entity.ArticleCategory;
import com.futengwl.service.ArticleCategoryService;

/**
 * 模板指令 - 顶级文章分类列表
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Component
public class ArticleCategoryRootListDirective extends BaseDirective {

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "articleCategories";

	@Inject
	private ArticleCategoryService articleCategoryService;

	/**
	 * 执行
	 * 
	 * @param env
	 *            环境变量
	 * @param params
	 *            参数
	 * @param loopVars
	 *            循环变量
	 * @param body
	 *            模板内容
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer count = getCount(params);
		boolean useCache = useCache(params);

		List<ArticleCategory> articleCategories = articleCategoryService.findRoots(count, useCache);
		setLocalVariable(VARIABLE_NAME, articleCategories, env, body);
	}

}