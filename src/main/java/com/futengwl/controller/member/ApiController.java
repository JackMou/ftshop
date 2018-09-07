package com.futengwl.controller.member;

import com.futengwl.Filter;
import com.futengwl.Order;
import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Member;
import com.futengwl.entity.MemberSpreadIncome;
import com.futengwl.entity.WithdrawRecord;
import com.futengwl.service.MemberService;
import com.futengwl.service.MemberSpreadIncomeService;
import com.futengwl.service.OrderService;
import com.futengwl.service.WeixinWithdrawService;
import com.futengwl.util.DateUtils;
import com.futengwl.util.FilterUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.auth.Subject;
import java.math.BigDecimal;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: ApiController
 * @package: com.futengwl.controller.api
 * @description:
 * @author: hanbin
 * @date: 2018-06-04  15:32
 */
@Controller("memberApiController")
@RequestMapping("/api")
public class ApiController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);
    @Autowired
    private MemberSpreadIncomeService memberSpreadIncomeService;

    @Autowired
    private WeixinWithdrawService weixinWithdrawService;

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/spreadIncome", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> spreadIncome(Integer offset, Integer limit, String beginTime, String endTime, String order, String sort) {
        Map<String, Object> data = new HashMap<>();
        try {
            Pageable pageable = new Pageable(offset/limit+1, limit);
            if (null != order && null != sort) {
                pageable.setOrderDirection(Order.Direction.DESC.name().toLowerCase().equals(order.toLowerCase()) ? Order.Direction.DESC : Order.Direction.ASC);
                pageable.setOrderProperty(sort);
            }
            List<Filter> filters = FilterUtils
                    .wrapper()//
                    .addFilter(new Filter("status", Filter.Operator.EQ, 1))
                    .done();//
            pageable.setFilters(filters);
            Page<MemberSpreadIncome> pages = memberSpreadIncomeService.findAllByRecommenderId(null, beginTime, endTime, pageable);
            List<MemberSpreadIncome> contents = pages.getContent();
            if (!CollectionUtils.isEmpty(contents)) {
                for (MemberSpreadIncome mi : contents) {
                    Long recommeId = mi.getRecommenderId();
                    Member member = memberService.find(recommeId);
                    mi.setUserName(null == member?"":member.getName());
                }
            }
            BigDecimal sum = memberSpreadIncomeService.sumMoneyWithRecommenderId(1, beginTime, endTime);
            data.put("data", pages);
            data.put("sum", sum);
            data.put("code", 200);
            return data;
        } catch (Exception ex) {
            data.put("code", 500);
            data.put("data", ex.getMessage());
            LOGGER.error(ex.getMessage());
            return data;
        }
    }

    @RequestMapping(value = "/withdraw", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> withdraw(Integer offset, Integer limit, String beginTime, String endTime, String order, String sort) {
        Map<String, Object> data = new HashMap<>();
        try {
            Pageable pageable = new Pageable(offset/limit+1, limit);
            if (null != order && null != sort) {
                pageable.setOrderDirection(Order.Direction.DESC.name().toLowerCase().equals(order.toLowerCase()) ? Order.Direction.DESC : Order.Direction.ASC);
                pageable.setOrderProperty(sort);
            }
            Page<WithdrawRecord> pages = weixinWithdrawService.withdrawRecord(beginTime, endTime, pageable);
            BigDecimal sum = weixinWithdrawService.sumWithdrawRecord(beginTime, endTime);
            data.put("data", pages);
            data.put("sum", sum);
            data.put("code", 200);
            return data;
        } catch (Exception ex) {
            data.put("code", 500);
            data.put("data", ex.getMessage());
            LOGGER.error(ex.getMessage());
            return data;
        }
    }

    @RequestMapping(value = "/spread", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> spread(Integer offset, Integer limit) {
        Map<String, Object> data = new HashMap<>();
        try {
            org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
            if (null == currentUser.getPrincipal()) {
                data.put("data", "没有登录");
                data.put("code", 300);
            }
            LOGGER.info("---------->" + currentUser.getPrincipal());
            Member member = memberService.findByUsername(currentUser.getPrincipal().toString());
            Long recommenderId = member.getId();
            Pageable pageable = new Pageable(offset/limit+1, limit);
            List<Filter> filters = FilterUtils
                    .wrapper()//
                    .addFilter(new Filter("status", Filter.Operator.EQ, 1))
                    .done();//
            pageable.setFilters(filters);
            pageable.setOrderDirection(Order.Direction.DESC);
            pageable.setOrderProperty("lastModifiedDate");
            Page<MemberSpreadIncome> pages = memberSpreadIncomeService.findAllByRecommenderId(recommenderId, null, null, pageable);
            BigDecimal sum = memberSpreadIncomeService.sumMoney(recommenderId, 1);
            data.put("sum", sum);
            data.put("data", pages);
            data.put("code", 200);
            return data;
        } catch (Exception ex) {
            data.put("code", 500);
            data.put("data", "服务异常!");
            LOGGER.error(ex.getMessage());
            return data;
        }
    }

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> order(String orderSn) {
        Map<String, Object> data = new HashMap<>();
        try {
            com.futengwl.entity.Order order = orderService.findBySn(orderSn);
            if(null!=order) {
                data.put("code", 200);
                data.put("name", order.getMember().getName());
            }
            return data;
        } catch (Exception ex) {
            data.put("code", 500);
            data.put("data", "服务异常!");
            LOGGER.error(ex.getMessage());
            return data;
        }
    }
}
