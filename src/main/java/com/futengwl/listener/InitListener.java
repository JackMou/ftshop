/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: UiPshnbtc44nPFm55k9iEc7IDbLQXoYA
 */
package com.futengwl.listener;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.futengwl.entity.Article;
import com.futengwl.entity.Product;
import com.futengwl.entity.Store;
import com.futengwl.service.ConfigService;
import com.futengwl.service.SearchService;

/**
 * Listener - 初始化
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Component
public class InitListener {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(InitListener.class.getName());

	@Value("${system.version}")
	private String systemVersion;

	@Inject
	private ConfigService configService;
	@Inject
	private SearchService searchService;

	/**
	 * 事件处理
	 * 
	 * @param contextRefreshedEvent
	 *            ContextRefreshedEvent
	 */
	@EventListener
	public void handle(ContextRefreshedEvent contextRefreshedEvent) {
		if (contextRefreshedEvent.getApplicationContext() == null || contextRefreshedEvent.getApplicationContext().getParent() != null) {
			return;
		}

		String info = "I|n|i|t|i|a|l|i|z|i|n|g| |F|T|S|H|O|P| |" + systemVersion;
		LOGGER.info(info.replace("|", StringUtils.EMPTY));
		configService.init();
		searchService.index(Article.class);
		searchService.index(Product.class);
		searchService.index(Store.class);
	}

}