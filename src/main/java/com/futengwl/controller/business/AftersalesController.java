/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 97EqwKC+qtXCaij5wTJqKetp70u5iW0g
 */
package com.futengwl.controller.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.futengwl.activeMQ.AsyncSendNotification;
import com.futengwl.entity.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futengwl.Pageable;
import com.futengwl.Results;
import com.futengwl.Setting;
import com.futengwl.exception.UnauthorizedException;
import com.futengwl.security.CurrentStore;
import com.futengwl.service.AftersalesService;
import com.futengwl.service.OrderShippingService;
import com.futengwl.util.SystemUtils;

/**
 * Controller - 售后
 *
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("businessAftersalesController")
@RequestMapping("business/aftersales")
public class AftersalesController extends BaseController {

    @Inject
    private AftersalesService aftersalesService;
    @Inject
    private OrderShippingService orderShippingService;

    /**
     * 添加属性
     */
    @ModelAttribute
    public void populateModel(Long aftersalesId, @CurrentStore Store currentStore, ModelMap model) {
        Aftersales aftersales = aftersalesService.find(aftersalesId);
        if (aftersales != null && !currentStore.equals(aftersales.getStore())) {
            throw new UnauthorizedException();
        }
        model.addAttribute("aftersales", aftersales);
    }

    /**
     * 物流动态
     */
    @GetMapping("/transit_step")
    public ResponseEntity<?> transitStep(@ModelAttribute(binding = false) Aftersales aftersales, @CurrentStore Store currentStore) {
        Map<String, Object> data = new HashMap<>();

        Setting setting = SystemUtils.getSetting();
        if (StringUtils.isEmpty(setting.getKuaidi100Customer()) || StringUtils.isEmpty(setting.getKuaidi100Key()) || StringUtils.isEmpty(aftersales.getDeliveryCorpCode()) || StringUtils.isEmpty(aftersales.getTrackingNo())) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        data.put("transitSteps", orderShippingService.getTransitSteps(aftersales.getDeliveryCorpCode(), aftersales.getTrackingNo()));
        return ResponseEntity.ok(data);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Aftersales.Type type, Aftersales.Status status, @CurrentStore Store currentStore, Pageable pageable, ModelMap model) {
        model.addAttribute("types", Aftersales.Type.values());
        model.addAttribute("statuses", Aftersales.Status.values());
        model.addAttribute("type", type);
        model.addAttribute("status", status);
        model.addAttribute("page", aftersalesService.findPage(type, status, null, currentStore, pageable));
        return "business/aftersales/list";
    }

    /**
     * 查看
     */
    @GetMapping("/view")
    public String view(@ModelAttribute(binding = false) Aftersales aftersales, ModelMap model) {
        if (aftersales == null) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }
        if (CollectionUtils.isEmpty(aftersales.getOrderItems())) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }

        Setting setting = SystemUtils.getSetting();
        model.addAttribute("isKuaidi100Enabled", StringUtils.isNotEmpty(setting.getKuaidi100Customer()) && StringUtils.isNotEmpty(setting.getKuaidi100Key()));
        model.addAttribute("aftersales", aftersales);
        return "business/aftersales/view";
    }

    /**
     * 审核
     */
    @PostMapping("/review")
    public ResponseEntity<?> review(@ModelAttribute(binding = false) Aftersales aftersales, boolean passed) {
        if (aftersales == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (!Aftersales.Status.PENDING.equals(aftersales.getStatus())) {
            return Results.UNPROCESSABLE_ENTITY;
        }

        aftersalesService.review(aftersales, passed);
        return Results.OK;
    }

    @Inject
    private AsyncSendNotification asyncSendNotification;

    /**
     * 完成
     */
    @PostMapping("/complete")
    public ResponseEntity<?> complete(@ModelAttribute(binding = false) Aftersales aftersales) {
        if (aftersales == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (!Aftersales.Status.APPROVED.equals(aftersales.getStatus())) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        aftersalesService.complete(aftersales);
        //将退货记录加入mq进行退款记录
        try {
            //判断此订单号是否已经发到生产端
            List<AftersalesItem> aftersalesItemList = aftersales.getAftersalesItems();
            List<Map<String, String>> mapList = new ArrayList<>();
            System.out.println("=====退货商品========" + aftersalesItemList.size());
            for (AftersalesItem aftersalesItem : aftersalesItemList) {
                OrderItem orderItem = aftersalesItem.getOrderItem();
                String proSn = orderItem.getSn().split("_")[0];
                System.out.println("=====退货商品编号========" + proSn);
                //将结算数据放入消息队列
                Map<String, String> map = new HashMap();
                map.put("orderId", orderItem.getOrder().getSn());
                map.put("orderMoney", orderItem.getPrice().multiply(new BigDecimal(aftersalesItem.getQuantity())).toString());
                map.put("proSn", proSn);
                map.put("price", orderItem.getPrice().toString());
                map.put("quantity", aftersalesItem.getQuantity().toString());
                System.out.println("=====退货生产端数据========" + map.toString());
                mapList.add(map);
            }
            asyncSendNotification.sendorderAfterSales(mapList);
            System.out.println("=====退货生产数据成功========" + mapList.toString());
        } catch (Exception e) {
            System.out.println("=====退货生产数据异常========" + e.getMessage());
            e.printStackTrace();
        }
        return Results.OK;
    }

}