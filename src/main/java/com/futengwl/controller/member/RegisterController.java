/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: mIbwfwK6oxLDSefQCZjKpvkbjNT+rAQr
 */
package com.futengwl.controller.member;

import com.futengwl.Results;
import com.futengwl.Setting;
import com.futengwl.entity.*;
import com.futengwl.security.UserAuthenticationToken;
import com.futengwl.service.*;
import com.futengwl.util.SystemUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Controller - 会员注册
 *
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("memberRegisterController")
@RequestMapping("/member/register")
public class RegisterController extends BaseController {

    @Inject
    private PluginService pluginService;
    @Inject
    private UserService userService;
    @Inject
    private MemberService memberService;
    @Inject
    private DistributorService distributorService;
    @Inject
    private MemberRankService memberRankService;
    @Inject
    private MemberAttributeService memberAttributeService;
    @Inject
    private SocialUserService socialUserService;

    /**
     * 检查用户名是否存在
     */
    @GetMapping("/check_username")
    public
    @ResponseBody
    boolean checkUsername(String username) {
        return StringUtils.isNotEmpty(username) && !memberService.usernameExists(username);
    }
    /**
     * 检查用户名是否存在
     */
    @GetMapping("/check_name")
    public
    @ResponseBody
    boolean checkName(String username) {
        return StringUtils.isNotEmpty(username) && !memberService.NameExists(username);
    }

    /**
     * 检查E-mail是否存在
     */
    @GetMapping("/check_email")
    public
    @ResponseBody
    boolean checkEmail(String email) {
        return StringUtils.isNotEmpty(email) && !memberService.emailExists(email);
    }

    /**
     * 检查手机是否存在
     */
    @GetMapping("/check_mobile")
    public
    @ResponseBody
    boolean checkMobile(String mobile) {
        return StringUtils.isNotEmpty(mobile) && !memberService.mobileExists(mobile);
    }

    /**
     * 注册页面
     */
    @GetMapping
    public String index(Long socialUserId, String uniqueId,String loginPluginId, String redirectUrl,HttpServletRequest request, ModelMap model) {
        if (socialUserId != null && StringUtils.isNotEmpty(uniqueId)) {
            SocialUser socialUser = socialUserService.find(socialUserId);
            if (socialUser == null || socialUser.getUser() != null || !StringUtils.equals(socialUser.getUniqueId(), uniqueId)) {
                return UNPROCESSABLE_ENTITY_VIEW;
            }
            model.addAttribute("socialUserId", socialUserId);
            model.addAttribute("uniqueId", uniqueId);
            model.addAttribute("loginPluginId", loginPluginId);
        }
        model.addAttribute("genders", Member.Gender.values());
        model.addAttribute("loginPlugins", pluginService.getActiveLoginPlugins(request));

        if(StringUtils.isNotEmpty(redirectUrl)){
            model.addAttribute("successLoginRedirectUrl",redirectUrl);
        }
        return "member/register/index";
    }

    @Inject
    private AuthCodeService authCodeService;

    /**
     * 注册提交
     */
    @PostMapping("/submit")
    public ResponseEntity<?> submit(String username, String password, String authCode, String spreadMemberUsername, HttpServletRequest request) {

        //检查用户是否存在，若存在，直接登陆
        //否则，先注册再登陆
        if(!checkUsername(username)){
            try {
                userService.login(new UserAuthenticationToken(Member.class, username, authCode, false, request.getRemoteAddr()));
                Member pMember = memberService.findByUsername(username);
                Member member = new Member();
                member.setId(pMember.getId());
                //member.removeAttributeValue();
                for (MemberAttribute memberAttribute : memberAttributeService.findList(true, true)) {
                    String[] values = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
                    String value = ArrayUtils.isNotEmpty(values) ? values[0].trim() : null;
                    if(null!=value&&!StringUtils.isEmpty(value)) {
                        if (!memberAttributeService.isValid(memberAttribute, values)) {
                            return Results.UNPROCESSABLE_ENTITY;
                        }
                        Object memberAttributeValue = memberAttributeService.toMemberAttributeValue(memberAttribute, values);
                        member.setAttributeValue(memberAttribute, memberAttributeValue);
                    }
                }
                if(null!=member.getName()&&StringUtils.isNotEmpty(member.getName())) {
                     memberService.update(member, "username", "email", "mobile", "encodedPassword", "point", "balance", "frozenAmount", "amount", "lastLoginIp", "lastLoginDate", "loginPluginId", "safeKey", "distributor", "cart", "orders", "socialUsers", "paymentTransactions", "memberDepositLogs", "couponCodes", "receivers",
                            "reviews", "consultations", "productFavorites", "productNotifies", "pointLogs", "isEnabled", "isBaseMember", "isLocked","memberRank");
                }else{
                     memberService.update(member, "username", "email", "mobile", "encodedPassword", "point", "balance", "frozenAmount", "amount", "lastLoginIp", "lastLoginDate", "loginPluginId", "safeKey", "distributor", "cart", "orders", "socialUsers", "paymentTransactions", "memberDepositLogs", "couponCodes", "receivers",
                            "reviews", "consultations", "productFavorites", "productNotifies", "pointLogs", "isEnabled", "isBaseMember", "isLocked", "name","memberRank");
                }
                //
                //更新SocialUser的userId
                String uniqueId = request.getParameter("uniqueId");
                String loginPluginId = request.getParameter("loginPluginId");
                if(null!=uniqueId && StringUtils.isNotEmpty(uniqueId) && null!=loginPluginId && StringUtils.isNotEmpty(loginPluginId)){
                    SocialUser socialUser = socialUserService.find(loginPluginId,uniqueId);
                    Member memberU = memberService.find(member.getId());
                    socialUser.setUser(memberU);
                    socialUser = socialUserService.update(socialUser);
                }

                return Results.OK;
            }catch (Exception ex){
                return Results.unprocessableEntity("验证码失效或者错误，请重新获取");
            }
        }
        //验证短信验证码是否正确
        if(!authCodeService.isValidAuthCode(username,"register",authCode)){
            return Results.unprocessableEntity("member.register.authCodeError");
        }

        //初始化
        String mobile=username;

        Setting setting = SystemUtils.getSetting();
        if (!ArrayUtils.contains(setting.getAllowedRegisterTypes(), Setting.RegisterType.MEMBER)) {
            return Results.unprocessableEntity("member.register.disabled");
        }
        if (!isValid(Member.class, "username", username, BaseEntity.Save.class)  || !isValid(Member.class, "mobile", mobile, BaseEntity.Save.class)) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (memberService.usernameExists(username)) {
            return Results.unprocessableEntity("member.register.usernameExist");
        }
//        if (memberService.emailExists(email)) {
//            return Results.unprocessableEntity("member.register.emailExist");
//        }
//        if (memberService.mobileExists(mobile)) {
//            return Results.unprocessableEntity("member.register.mobileExist");
//        }


        Member member = new Member();
        member.removeAttributeValue();
        for (MemberAttribute memberAttribute : memberAttributeService.findList(true, true)) {
            String[] values = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
            if (!memberAttributeService.isValid(memberAttribute, values)) {
                return Results.UNPROCESSABLE_ENTITY;
            }
            Object memberAttributeValue = memberAttributeService.toMemberAttributeValue(memberAttribute, values);
            member.setAttributeValue(memberAttribute, memberAttributeValue);
        }

        member.setUsername(username);
        member.setPassword(UUID.randomUUID().toString());//不使用，但必须有。
        member.setEmail(null);
        member.setMobile(mobile);
        member.setPoint(0L);
        member.setBalance(BigDecimal.ZERO);
        member.setFrozenAmount(BigDecimal.ZERO);
        member.setAmount(BigDecimal.ZERO);
        member.setIsEnabled(true);
        member.setIsLocked(false);
        member.setLockDate(null);
        member.setLastLoginIp(request.getRemoteAddr());
        member.setLastLoginDate(new Date());
        member.setSafeKey(null);
        member.setMemberRank(memberRankService.findDefault());
        member.setDistributor(null);
        member.setCart(null);
        member.setOrders(null);
        member.setPaymentTransactions(null);
        member.setMemberDepositLogs(null);
        member.setCouponCodes(null);
        member.setReceivers(null);
        member.setReviews(null);
        member.setConsultations(null);
        member.setProductFavorites(null);
        member.setProductNotifies(null);
        member.setSocialUsers(null);
        member.setPointLogs(null);
        try {
            Cookie[] cookies = request.getCookies();
            String bpId = "";
            for (Cookie cookie : cookies) {
                if ("bpId".equals(cookie.getName())) {
                    bpId = cookie.getValue();
                }
            }
            //保存用户美容院绑定关系
            userService.register(member, bpId);
        } catch (Exception ex) {
            return Results.ERROR;
        }
        //更新SocialUser的userId
        String uniqueId = request.getParameter("uniqueId");
        String loginPluginId = request.getParameter("loginPluginId");
        SocialUser socialUser = socialUserService.find(loginPluginId,uniqueId);
        if(null!=socialUser) {
            socialUser.setUser(member);
            socialUserService.update(socialUser);
        }

        userService.login(new UserAuthenticationToken(Member.class, username, authCode, false, request.getRemoteAddr()));
        Member spreadMember = memberService.findByUsername(spreadMemberUsername);
        if (spreadMember != null) {
            distributorService.create(member, spreadMember);
        }

        return Results.OK;
    }

    /**
     * 获取注册验证码
     */
    @PostMapping("/authCode")
    public @ResponseBody String
    sendAuthCode(String username) {
        if(authCodeService.sendAuthCode(username,"register")){
            return "success";
        }
        return "failure";
    }

}