/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: FOjKvNAXcE9cf5uI2MhJM2zEWLDGOFm6
 */
package com.futengwl.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.futengwl.ExcelView;
import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.Results;
import com.futengwl.Setting;
import com.futengwl.entity.Member;
import com.futengwl.entity.Order;
import com.futengwl.entity.OrderPayment;
import com.futengwl.entity.OrderRefunds;
import com.futengwl.entity.OrderShipping;
import com.futengwl.service.DeliveryCorpService;
import com.futengwl.service.MemberService;
import com.futengwl.service.OrderService;
import com.futengwl.service.OrderShippingService;
import com.futengwl.service.PaymentMethodService;
import com.futengwl.service.ShippingMethodService;
import com.futengwl.util.JsonUtils;
import com.futengwl.util.SystemUtils;
import com.futengwl.util.WebUtils;

/**
 * Controller - 订单
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("adminOrderController")
@RequestMapping("/admin/order")
public class OrderController extends BaseController {

	@Inject
	private OrderService orderService;
	@Inject
	private ShippingMethodService shippingMethodService;
	@Inject
	private PaymentMethodService paymentMethodService;
	@Inject
	private DeliveryCorpService deliveryCorpService;
	@Inject
	private OrderShippingService orderShippingService;
	@Inject
	private MemberService memberService;

	/**
	 * 物流动态
	 */
	@GetMapping("/transit_step")
	public ResponseEntity<?> transitStep(Long shippingId) {
		Map<String, Object> data = new HashMap<>();
		OrderShipping orderShipping = orderShippingService.find(shippingId);
		if (orderShipping == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		Setting setting = SystemUtils.getSetting();
		if (StringUtils.isEmpty(setting.getKuaidi100Customer()) || StringUtils.isEmpty(setting.getKuaidi100Key()) || StringUtils.isEmpty(orderShipping.getDeliveryCorpCode()) || StringUtils.isEmpty(orderShipping.getTrackingNo())) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		data.put("transitSteps", orderShippingService.getTransitSteps(orderShipping));
		return ResponseEntity.ok(data);
	}

	/**
	 * 查看
	 */
	@GetMapping("/view")
	public String view(Long id, ModelMap model) {
		Setting setting = SystemUtils.getSetting();
		model.addAttribute("methods", OrderPayment.Method.values());
		model.addAttribute("refundsMethods", OrderRefunds.Method.values());
		model.addAttribute("paymentMethods", paymentMethodService.findAll());
		model.addAttribute("shippingMethods", shippingMethodService.findAll());
		model.addAttribute("deliveryCorps", deliveryCorpService.findAll());
		model.addAttribute("isKuaidi100Enabled", StringUtils.isNotEmpty(setting.getKuaidi100Customer()) && StringUtils.isNotEmpty(setting.getKuaidi100Key()));
		model.addAttribute("order", orderService.find(id));
		return "admin/order/view";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Date beginDate, Date endDate, Order.Type type, Order.Status status, String memberUsername, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable, ModelMap model) {
		model.addAttribute("types", Order.Type.values());
		model.addAttribute("statuses", Order.Status.values());
		model.addAttribute("type", type);
		model.addAttribute("status", status);
		model.addAttribute("memberUsername", memberUsername);
		model.addAttribute("isPendingReceive", isPendingReceive);
		model.addAttribute("isPendingRefunds", isPendingRefunds);
		model.addAttribute("isAllocatedStock", isAllocatedStock);
		model.addAttribute("hasExpired", hasExpired);
		model.addAttribute("beginDate", beginDate);
        model.addAttribute("endDate", endDate);
        
        //start add test
        model.addAttribute("paymentMethods", paymentMethodService.findAll());
        //end
        
		Member member = memberService.findByUsername(memberUsername);
		if (StringUtils.isNotEmpty(memberUsername) && member == null) {
			model.addAttribute("page", Page.emptyPage(pageable));
		} else {
			model.addAttribute("page", orderService.findPage(beginDate,endDate,type, status, null, member, null, isPendingReceive, isPendingRefunds, null, null, isAllocatedStock, hasExpired, pageable));
		}
		return "admin/order/list";
	}
	
	
	@Value("${beauty.querymember.url}")
    String url;
	
	 /**
     * 导出列表
     */
    @GetMapping("/download")
    public ModelAndView listDownload(Date beginDate, Date endDate, Order.Type type, Order.Status status, String memberUsername, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable, ModelMap model) {
        if(pageable==null){
            pageable=new Pageable();
        }
        pageable.setPageSize(1000);

        if(beginDate!=null) model.addAttribute("beginDate",DateFormatUtils.format(beginDate,"yyyyMMddHHmmss"));
        if(endDate!=null) model.addAttribute("endDate",DateFormatUtils.format(endDate,"yyyyMMddHHmmss"));
        
        if(null==hasExpired){
            hasExpired=false;
        }

        Member member = memberService.findByUsername(memberUsername);
        if (StringUtils.isNotEmpty(memberUsername) && member == null) {
        	System.out.println(memberUsername);
            model.addAttribute("orders",Page.emptyPage(pageable).getContent());
        } else {
            Page page = orderService.findPage(beginDate, endDate,type, status, null, member, null, isPendingReceive, isPendingRefunds, null, null, isAllocatedStock, hasExpired, pageable);
            List orders = page.getContent();
            for(int i=page.getPageNumber()+1;i<=page.getTotalPages();i++){
                pageable.setPageNumber(i);
                page = orderService.findPage(beginDate, endDate,type, status, null, member, null, isPendingReceive, isPendingRefunds, null, null, isAllocatedStock, hasExpired, pageable);
                
                orders.addAll(page.getContent());
				
            }
            for (Object order : orders) {
				Long memberId = ((Order)order).getMember().getId();
				Map params = new HashMap<String, Object>();
				params.put("memberId", memberId);
				String resultJson = WebUtils.get(url, params);
				if (org.springframework.util.StringUtils.hasText(resultJson)) {
					Map map = JsonUtils.toObject(resultJson, Map.class);
					((Order)order).getMember().setBpName(map.get("bpName").toString());
					
				}
			}
            model.addAttribute("orders",orders);
        }
        
        
        
        String filename = "orders_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls";
        return new ModelAndView(new ExcelView("/admin/order/download.xls", filename), model);
    }

}