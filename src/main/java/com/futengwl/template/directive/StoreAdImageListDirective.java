/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 3y1x+W6pNTmqJ0PczzFfXnfUCemSQpzZ
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
import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.entity.Consultation;
import com.futengwl.entity.StoreAdImage;
import com.futengwl.service.StoreAdImageService;
import com.futengwl.util.FreeMarkerUtils;

/**
 * 模板指令 - 店铺广告图片列表
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Component
public class StoreAdImageListDirective extends BaseDirective {

	/**
	 * "店铺ID"参数名称
	 */
	private static final String STORE_ID_PARAMETER_NAME = "storeId";

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "storeAdImages";

	@Inject
	private StoreAdImageService storeAdImageService;

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
		Long storeId = FreeMarkerUtils.getParameter(STORE_ID_PARAMETER_NAME, Long.class, params);
		Integer count = getCount(params);
		List<Filter> filters = getFilters(params, Consultation.class);
		List<Order> orders = getOrders(params);
		boolean useCache = useCache(params);

		List<StoreAdImage> storeAdImages = storeAdImageService.findList(storeId, count, filters, orders, useCache);
		setLocalVariable(VARIABLE_NAME, storeAdImages, env, body);
	}

}