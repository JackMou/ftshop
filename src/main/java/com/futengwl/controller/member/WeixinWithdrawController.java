/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: s9wp7lZ+Ta/yNc4tyCaCpsCbk2oUTBt5
 */
package com.futengwl.controller.member;

import com.futengwl.entity.Member;
import com.futengwl.entity.SocialUser;
import com.futengwl.entity.UserAccount;
import com.futengwl.entity.WithdrawRecord;
import com.futengwl.security.CurrentUser;
import com.futengwl.service.MemberService;
import com.futengwl.service.UserAccountService;
import com.futengwl.service.WeixinWithdrawService;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Controller - 提现
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("weixinWithdrawController")
@RequestMapping("/member/weixinWithdraw")
public class WeixinWithdrawController extends BaseController {

	@Inject
	private WeixinWithdrawService weixinWithdrawService;
	@Inject
	private UserAccountService userAccountService;
	@Inject
	private MemberService  memberService;

	/**
	 * 提现
	 */
	@GetMapping("/doWithdraw")
	public ResponseEntity<?>  doWithdraw(@CurrentUser Member member, HttpServletRequest request) {
		Map returnMap = new HashMap();
//		returnMap.put("code","1");
//		returnMap.put("msg","提现功能暂未开放！");
//		return new ResponseEntity(returnMap,HttpStatus.OK);
		try{
            String openId=null;
			Map<String,String> map = new HashMap();
			Set<SocialUser> socialUsers=member.getSocialUsers();
            SocialUser socialUser = (SocialUser)request.getSession().getAttribute("socialUser");
            if("weixinPublicLoginPlugin".equals(socialUser.getLoginPluginId())&&socialUsers.contains(socialUser)){
                openId=socialUser.getUniqueId();
            }

            if(StringUtils.isEmpty(openId)){
                returnMap.put("code","1");
                returnMap.put("msg","请用微信打开后进行提现！");
                return new ResponseEntity(returnMap,HttpStatus.OK);
            }
            map.put("openId",openId);
            map.put("userId",String.valueOf(member.getId()));
			//map.put("widthdrawAmount",request.getParameter("widthdrawAmount"));

			Map<String,String> resultMap = weixinWithdrawService.doWithdraw(map);
			if(resultMap.containsKey("1")){
				returnMap.put("code","1");
				returnMap.put("msg",resultMap.get("1"));
				return new ResponseEntity(returnMap,HttpStatus.OK);
			}else{
				returnMap.put("code","0");
				returnMap.put("msg","提现成功");
			}
		}catch (Exception ex){
			ex.printStackTrace();
			returnMap.put("code","1");
			returnMap.put("msg","提现异常");
			return new ResponseEntity(returnMap,HttpStatus.OK);
		}
		return new ResponseEntity(returnMap,HttpStatus.OK);
	}


	/**
	 * 提现记录 列表
	 */
	@GetMapping("/queryWithdrawRecordList")
	public ResponseEntity<?> queryWithdrawRecordList(@CurrentUser Member member,HttpServletRequest request) {
		Map map = new HashMap();
		map.put("userId",member.getId());
		List<WithdrawRecord> withdrawRecordList = null;
		try {
			withdrawRecordList = weixinWithdrawService.queryWithdrawRecordList(map);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.BAD_GATEWAY);
		}
		return new ResponseEntity(withdrawRecordList,HttpStatus.OK);
	}

	/**
	 * 查询余额
	 */
	@GetMapping("/getUserAccount")
	public ResponseEntity<?> getUserAccount( @CurrentUser Member member,HttpServletRequest request) {
		Map map = new HashMap();
		map.put("userId",member.getId());
		UserAccount userAccount = userAccountService.getUserAccount(map);
		if(userAccount==null ||userAccount.getBalance()==null){
			userAccount = new UserAccount();
			userAccount.setCreatedDate(new Date());
			userAccount.setBalance(new BigDecimal(0));
		}
		return new ResponseEntity(userAccount,HttpStatus.OK);
	}

}