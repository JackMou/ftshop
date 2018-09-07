/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: d5rg7XklJcN7G+pBx96tYMFCkOrl5Ap4
 */
package com.futengwl.job;

import java.util.Calendar;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.futengwl.service.StatisticService;

/**
 * Job - 统计
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Lazy(false)
@Component
public class StatisticJob {

	@Inject
	private StatisticService statisticService;

	/**
	 * 收集
	 */
	@Scheduled(cron = "${job.statistic_collect.cron}")
	public void collect() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		statisticService.collect(year, month, day);
	}
}