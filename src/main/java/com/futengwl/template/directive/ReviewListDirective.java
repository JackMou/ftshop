/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: yyUtbOaAX4q07FRs1GxbIfA2Xbp+Ewc/
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
import com.futengwl.entity.Review;
import com.futengwl.service.ReviewService;
import com.futengwl.util.FreeMarkerUtils;

/**
 * 模板指令 - 评论
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Component
public class ReviewListDirective extends BaseDirective {

	/**
	 * "会员ID"参数名称
	 */
	private static final String MEMBER_ID_PARAMETER_NAME = "memberId";

	/**
	 * "商品ID"参数名称
	 */
	private static final String PRODUCT_ID_PARAMETER_NAME = "productId";

	/**
	 * "类型"参数名称
	 */
	private static final String TYPE_PARAMETER_NAME = "type";

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "reviews";

	@Inject
	private ReviewService reviewService;

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
		Long memberId = FreeMarkerUtils.getParameter(MEMBER_ID_PARAMETER_NAME, Long.class, params);
		Long productId = FreeMarkerUtils.getParameter(PRODUCT_ID_PARAMETER_NAME, Long.class, params);
		Review.Type type = FreeMarkerUtils.getParameter(TYPE_PARAMETER_NAME, Review.Type.class, params);
		Integer count = getCount(params);
		List<Filter> filters = getFilters(params, Review.class);
		List<Order> orders = getOrders(params);
		boolean useCache = useCache(params);

		List<Review> reviews = reviewService.findList(memberId, productId, type, true, count, filters, orders, useCache);
		setLocalVariable(VARIABLE_NAME, reviews, env, body);
	}

}