/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: aoPq8Ga+rO7y3tgNQ5XMp/+Mp6wvS9AD
 */
package com.futengwl.controller.member;

import com.futengwl.Pageable;
import com.futengwl.Results;
import com.futengwl.entity.Member;
import com.futengwl.entity.Receiver;
import com.futengwl.exception.UnauthorizedException;
import com.futengwl.security.CurrentUser;
import com.futengwl.service.AreaService;
import com.futengwl.service.ReceiverService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

/**
 * Controller - 收货地址
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("memberWalletController")
@RequestMapping("/member/wallet")
public class WalletController extends BaseController {

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Integer pageNumber, @CurrentUser Member currentUser, ModelMap model) {
		//Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		//model.addAttribute("page", receiverService.findPage(currentUser, pageable));
		return "member/wallet/list";
	}
	/**
	 * 列表
	 */
	@GetMapping("/info")
	public String info(Integer pageNumber, @CurrentUser Member currentUser, ModelMap model) {
		//Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		//model.addAttribute("page", receiverService.findPage(currentUser, pageable));
		return "member/wallet/info";
	}


}