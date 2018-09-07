/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: LTLXGUNVd/p2ZpsxWr+7pQ5K5lJ2p4gR
 */
package com.futengwl.template.method;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import com.futengwl.util.FreeMarkerUtils;
import com.futengwl.util.SpringUtils;

/**
 * 模板方法 - 多语言
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Component
public class MessageMethod implements TemplateMethodModelEx {

	/**
	 * 执行
	 * 
	 * @param arguments
	 *            参数
	 * @return 结果
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		String code = FreeMarkerUtils.getArgument(0, String.class, arguments);
		if (StringUtils.isNotEmpty(code)) {
			String message;
			if (arguments.size() > 1) {
				Object[] args = new Object[arguments.size() - 1];
				for (int i = 1; i < arguments.size(); i++) {
					Object argument = arguments.get(i);
					if (argument != null && argument instanceof TemplateModel) {
						args[i - 1] = DeepUnwrap.unwrap((TemplateModel) argument);
					} else {
						args[i - 1] = argument;
					}
				}
				message = SpringUtils.getMessage(code, args);
			} else {
				message = SpringUtils.getMessage(code);
			}
			return new SimpleScalar(message);
		}
		return null;
	}

}