/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: ASGR71dBcTPZlbC6fmzgvygSrIkl/G5f
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
import com.futengwl.entity.MemberAttribute;
import com.futengwl.service.MemberAttributeService;

/**
 * 模板指令 - 会员注册项列表
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Component
public class MemberAttributeListDirective extends BaseDirective {

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "memberAttributes";

	@Inject
	private MemberAttributeService memberAttributeService;

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
		List<Filter> filters = getFilters(params, MemberAttribute.class);
		List<Order> orders = getOrders(params);
		boolean useCache = useCache(params);

		List<MemberAttribute> memberAttributes = memberAttributeService.findList(true, count, filters, orders, useCache);
		setLocalVariable(VARIABLE_NAME, memberAttributes, env, body);
	}

}