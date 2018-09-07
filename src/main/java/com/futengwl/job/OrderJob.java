/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: KWw3M74a/WiHpV/c2BnQtNCHKf7NGTfb
 */
package com.futengwl.job;

import com.futengwl.service.OrderService;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Job - 订单
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Lazy(false)
@Component
public class OrderJob {

	@Inject
	private OrderService orderService;

	/**
	 * 过期订单处理
	 */
	@Scheduled(cron = "${job.order_expired_processing.cron}")
	public void expiredProcessing() {
		orderService.expiredRefundHandle();
		orderService.undoExpiredUseCouponCode();
		orderService.undoExpiredExchangePoint();
		orderService.releaseExpiredAllocatedStock();
	}

	/**
	 * 自动收货
	 */
	@Scheduled(cron = "${job.order_automatic_receive.cron}")
	public void automaticReceive() {
		orderService.automaticReceive();
	}

}