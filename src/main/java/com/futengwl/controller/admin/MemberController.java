/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: ZrFz4qJScXtSD0OdopSHdVGvXI1ivPFP
 */
package com.futengwl.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.futengwl.ExcelView;
import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.Results;
import com.futengwl.audit.Audit;
import com.futengwl.entity.BaseEntity;
import com.futengwl.entity.Member;
import com.futengwl.entity.MemberAttribute;
import com.futengwl.service.MemberAttributeService;
import com.futengwl.service.MemberRankService;
import com.futengwl.service.MemberService;
import com.futengwl.service.UserService;
import com.futengwl.util.JsonUtils;
import com.futengwl.util.WebUtils;

/**
 * Controller - 会员
 *
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController extends BaseController {

    @Inject
    private MemberService memberService;
    @Inject
    private UserService userService;
    @Inject
    private MemberRankService memberRankService;
    @Inject
    private MemberAttributeService memberAttributeService;

    /**
     * 检查用户名是否存在
     */
    @GetMapping("/check_username")
    public @ResponseBody
    boolean checkUsername(String username) {
        return StringUtils.isNotEmpty(username) && !memberService.usernameExists(username);
    }

    /**
     * 检查E-mail是否唯一
     */
    @GetMapping("/check_email")
    public @ResponseBody
    boolean checkEmail(Long id, String email) {
        return StringUtils.isNotEmpty(email) && memberService.emailUnique(id, email);
    }

    /**
     * 检查手机是否唯一
     */
    @GetMapping("/check_mobile")
    public @ResponseBody
    boolean checkMobile(Long id, String mobile) {
        return StringUtils.isNotEmpty(mobile) && memberService.mobileUnique(id, mobile);
    }

    /**
     * 查看
     */
    @GetMapping("/view")
    public String view(Long id, ModelMap model) {
        Member member = memberService.find(id);
        model.addAttribute("genders", Member.Gender.values());
        model.addAttribute("memberAttributes", memberAttributeService.findList(true, true));
        model.addAttribute("member", member);
        return "admin/member/view";
    }

    /**
     * 添加
     */
    @GetMapping("/add")
    public String add(ModelMap model) {
        model.addAttribute("genders", Member.Gender.values());
        model.addAttribute("memberRanks", memberRankService.findAll());
        model.addAttribute("memberAttributes", memberAttributeService.findList(true, true));
        return "admin/member/add";
    }

    /**
     * 保存
     */
    @Audit(action = "auditLog.action.admin.member.save")
    @PostMapping("/save")
    public ResponseEntity<?> save(Member member, Long memberRankId, boolean isBMember, String beautyId, HttpServletRequest request) {
        if (isBMember) {
            member.setBaseMember(1);
        } else {
            member.setBaseMember(0);
        }
        member.setMemberRank(memberRankService.find(memberRankId));
        member.setMobile(member.getUsername());
        if (!isValid(member, BaseEntity.Save.class)) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (memberService.usernameExists(member.getUsername())) {
            return Results.UNPROCESSABLE_ENTITY;
        }
//		if (memberService.emailExists(member.getEmail())) {
//			return Results.UNPROCESSABLE_ENTITY;
//		}
//		if (memberService.mobileExists(member.getMobile())) {
//			return Results.UNPROCESSABLE_ENTITY;
//		}
        member.removeAttributeValue();
        for (MemberAttribute memberAttribute : memberAttributeService.findList(true, true)) {
            String[] values = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
            if (!memberAttributeService.isValid(memberAttribute, values)) {
                return Results.UNPROCESSABLE_ENTITY;
            }
            Object memberAttributeValue = memberAttributeService.toMemberAttributeValue(memberAttribute, values);
            member.setAttributeValue(memberAttribute, memberAttributeValue);
        }
        member.setPoint(0L);
        member.setBalance(BigDecimal.ZERO);
        member.setFrozenAmount(BigDecimal.ZERO);
        member.setAmount(BigDecimal.ZERO);
        member.setIsLocked(false);
        member.setLockDate(null);
        member.setLastLoginIp(null);
        member.setLastLoginDate(null);
        member.setDistributor(null);
        member.setSafeKey(null);
        member.setCart(null);
        member.setOrders(null);
        member.setSocialUsers(null);
        member.setPaymentTransactions(null);
        member.setMemberDepositLogs(null);
        member.setCouponCodes(null);
        member.setReceivers(null);
        member.setReviews(null);
        member.setConsultations(null);
        member.setProductFavorites(null);
        member.setProductNotifies(null);
        member.setPointLogs(null);
        member.setStoreFavorites(null);
        member.setFromMessages(null);
        member.setToMessages(null);
        member.setAuditLogs(null);
        member.setAftersales(null);
        member.setUser1MessageGroups(null);
        member.setUser2MessageGroups(null);

        //保存用户美容院绑定关系
        try {
            memberService.save(member);
            userService.updateRelation(String.valueOf(member.getId()), beautyId);
            return Results.OK;
        } catch (Exception ex) {
            return Results.unprocessableEntity("保存失败，请重试！");
        }


    }

    /**
     * 编辑
     */
    @GetMapping("/edit")
    public String edit(Long id, ModelMap model) {
        Member member = memberService.find(id);
        model.addAttribute("genders", Member.Gender.values());
        //获取会员对应的美容院信息，如果存在则返回给前端，如果不存在则不反回
        try {
            Map map = memberService.getBeauty(id);
            if (null != map && null != map.get("bpId")) {
                model.addAttribute("bpId", map.get("bpId"));
                model.addAttribute("bpName", map.get("bpName"));
            }
        }catch (Exception ex){
            model.addAttribute("bpId","error");
            model.addAttribute("bpName","error");
        }

        model.addAttribute("memberRanks", memberRankService.findAll());
        model.addAttribute("memberAttributes", memberAttributeService.findList(true, true));
        model.addAttribute("member", member);
        return "admin/member/edit";
    }

    /**
     * 更新
     */
    @Audit(action = "auditLog.action.admin.member.update")
    @PostMapping("/update")
    public ResponseEntity<?> update(Member member, Long id, Long memberRankId, Boolean unlock, Boolean isBMember, String beautyId, HttpServletRequest request) {
        if (null != isBMember && isBMember) {
            member.setBaseMember(1);
        } else {
            member.setBaseMember(0);
        }
        member.setMemberRank(memberRankService.find(memberRankId));

        if (!isValid(member)) {
            return Results.UNPROCESSABLE_ENTITY;
        }
//		if (!memberService.emailUnique(id, member.getEmail())) {
//			return Results.UNPROCESSABLE_ENTITY;
//		}
//		if (!memberService.mobileUnique(id, member.getMobile())) {
//			return Results.UNPROCESSABLE_ENTITY;
//		}
        //member.removeAttributeValue();
        for (MemberAttribute memberAttribute : memberAttributeService.findList(true, true)) {
            String[] values = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
            if (!memberAttributeService.isValid(memberAttribute, values)) {
                return Results.UNPROCESSABLE_ENTITY;
            }
            Object memberAttributeValue = memberAttributeService.toMemberAttributeValue(memberAttribute, values);
            member.setAttributeValue(memberAttribute, memberAttributeValue);
        }
        Member pMember = memberService.find(id);
        if (pMember == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (BooleanUtils.isTrue(pMember.getIsLocked()) && BooleanUtils.isTrue(unlock)) {
            userService.unlock(member);
            memberService.update(member, "username", "email", "mobile", "encodedPassword", "point", "balance", "frozenAmount", "amount", "lastLoginIp", "lastLoginDate", "loginPluginId", "safeKey", "distributor", "cart", "orders", "socialUsers", "paymentTransactions", "memberDepositLogs", "couponCodes", "receivers",
                    "reviews", "consultations", "productFavorites", "productNotifies", "pointLogs");
        } else {
            memberService.update(member, "username", "email", "mobile", "encodedPassword", "point", "balance", "frozenAmount", "amount", "isLocked", "lockDate", "lastLoginIp", "lastLoginDate", "loginPluginId", "safeKey", "distributor", "cart", "orders", "socialUsers", "paymentTransactions", "memberDepositLogs",
                    "couponCodes", "receivers", "reviews", "consultations", "productFavorites", "productNotifies", "pointLogs");
        }
        try {
            // 保存美容院关系
            if (null != beautyId && StringUtils.isNotEmpty(beautyId)) {
                userService.updateRelation(String.valueOf(member.getId()), beautyId);
            }
            return Results.OK;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return Results.unprocessableEntity("更新美容院关系失败！请重试。");
        }

    }
    
    @Value("${beauty.querymember.url}")
    private String querymemberURL;
    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Pageable pageable, ModelMap model) {
        model.addAttribute("memberRanks", memberRankService.findAll());
        model.addAttribute("memberAttributes", memberAttributeService.findAll());
        Page<Member> page = memberService.findPage(pageable);
        for (Member member : page.getContent()) {
        	Map<String, Object> params = new HashMap<String, Object>();
        	params.put("memberId", member.getId());
        	String resultJson = WebUtils.get(querymemberURL, params);
        	if(org.springframework.util.StringUtils.hasText(resultJson)) {
        		member.setBpName(JsonUtils.toObject(resultJson, Map.class).get("bpName").toString());
        	}
		}
        model.addAttribute("page", page);
        return "admin/member/list";
    }

    /**
     * 删除
     */
    @Audit(action = "auditLog.action.admin.member.delete")
    @PostMapping("/delete")
    public ResponseEntity<?> delete(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                Member member = memberService.find(id);
                if (member != null && member.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                    return Results.unprocessableEntity("admin.member.deleteExistDepositNotAllowed", member.getUsername());
                }
            }
            memberService.delete(ids);
        }
        return Results.OK;
    }

    @Value("${beauty.query.url}")
    private String url;

    /**
     * 美容院选择
     */
    @GetMapping("/beauty_select")
    public ResponseEntity<?> beautySelect(String keyword) {
        List<Map<String, Object>> data = new ArrayList<>();
        if (StringUtils.isEmpty(keyword)) {
            return ResponseEntity.ok(data);
        }
        //调用服务获取美容院列表

        Map params = new HashMap<String, Object>();
        params.put("keyword", keyword);
        String result = WebUtils.get(url, params);
        Map<String, Object> resultMap = JSON.parseObject(result);
        if (!"1".equals(resultMap.get("status"))) {
            return ResponseEntity.ok(data);
        }
        JSONArray arrayList = (JSONArray) resultMap.get("list");

        for (Object obj : arrayList) {

            Map<String, Object> item = new HashMap<String, Object>();
            item.put("id", ((Map<String, Object>) obj).get("bpId"));
            item.put("name", ((Map<String, Object>) obj).get("bpName"));
            data.add(item);

        }
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/download")
    public ModelAndView download(Pageable pageable, ModelMap model) {
    	Page<Member> page = memberService.findPage(pageable);
    	List<Member> members = page.getContent();
    	for(int i=page.getPageNumber()+1;i<=page.getTotalPages();i++){
            pageable.setPageNumber(i);
            page = memberService.findPage(pageable);
            members.addAll(page.getContent());
    	}
        for (Member member : members) {
        	Map<String, Object> params = new HashMap<String, Object>();
        	params.put("memberId", member.getId());
        	String resultJson = WebUtils.get(querymemberURL, params);
        	if(org.springframework.util.StringUtils.hasText(resultJson)) {
        		member.setBpName(JsonUtils.toObject(resultJson, Map.class).get("bpName").toString());
        	}
		}
        model.addAttribute("members", members);
        String filename = "users_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls";
        return new ModelAndView(new ExcelView("/admin/member/download.xls", filename), model);
    }
}