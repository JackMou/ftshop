/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 2NdalHkh97DI8rFeQmM1YdwZtL9vzZVS
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
import com.futengwl.entity.StoreProductCategory;
import com.futengwl.service.StoreProductCategoryService;
import com.futengwl.util.FreeMarkerUtils;

/**
 * 模板指令 - 顶级店铺商品分类列表
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Component
public class StoreProductCategoryRootListDirective extends BaseDirective {

	/**
	 * "店铺ID"参数名称
	 */
	private static final String STORE_ID = "storeId";

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "storeProductCategories";

	@Inject
	private StoreProductCategoryService storeProductCategoryService;

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
		Long storeId = FreeMarkerUtils.getParameter(STORE_ID, Long.class, params);
		Integer count = getCount(params);
		boolean useCache = useCache(params);

		List<StoreProductCategory> storeProductCategories = storeProductCategoryService.findRoots(storeId, count, useCache);
		setLocalVariable(VARIABLE_NAME, storeProductCategories, env, body);
	}

}