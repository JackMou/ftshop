/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: XvYAci70xNh1OtPg2pHlCX3OXmAYGcsd
 */
package com.futengwl.controller.member;

import javax.inject.Inject;

import com.futengwl.entity.Store;
import com.futengwl.exception.ResourceNotFoundException;
import com.futengwl.security.CurrentStore;
import com.futengwl.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futengwl.controller.shop.BaseController;
import com.futengwl.entity.Member;
import com.futengwl.entity.Order;
import com.futengwl.security.CurrentUser;

/**
 * Controller - 首页
 *
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("memberIndexController")
@RequestMapping("/member/index")
public class IndexController extends BaseController {

    /**
     * 最新订单数量
     */
    private static final int NEW_ORDER_SIZE = 3;

    @Inject
    private OrderService orderService;
    @Inject
    private CouponCodeService couponCodeService;
    @Inject
    private MessageService messageService;
    @Inject
    private ProductFavoriteService productFavoriteService;
    @Inject
    private ProductNotifyService productNotifyService;
    @Inject
    private ReviewService reviewService;
    @Inject
    private ConsultationService consultationService;
    @Inject
    private StoreService storeService;
    /**
     * 首页
     */
    @GetMapping
    public String index(@CurrentUser Member currentUser, ModelMap model, Long storeId) {
        model.addAttribute("pendingPaymentOrderCount", orderService.count(null, Order.Status.PENDING_PAYMENT, null, currentUser, null, null, null, null, null, null, false));
        model.addAttribute("pendingShipmentOrderCount", orderService.count(null, Order.Status.PENDING_SHIPMENT, null, currentUser, null, null, null, null, null, null, false));
        model.addAttribute("shippedOrderCount", orderService.count(null, Order.Status.SHIPPED, null, currentUser, null, null, null, null, null, null, null));
        model.addAttribute("unreadMessageCount", messageService.unreadMessageCount(null, currentUser));
        model.addAttribute("couponCodeCount", couponCodeService.count(null, currentUser, null, false, false));
        model.addAttribute("productFavoriteCount", productFavoriteService.count(currentUser));
        model.addAttribute("productNotifyCount", productNotifyService.count(currentUser, null, null, null));
        model.addAttribute("reviewCount", reviewService.count(currentUser, null, null, null));
        model.addAttribute("consultationCount", consultationService.count(currentUser, null, null));
        model.addAttribute("newOrders", orderService.findList(null, null, null, currentUser, null, null, null, null, null, null, null, NEW_ORDER_SIZE, null, null));

        if(storeId!=null){
            model.addAttribute("storeId", storeId);
            model.addAttribute("homeURL", "/store/"+storeId);
        }
        return "member/index";
    }

}