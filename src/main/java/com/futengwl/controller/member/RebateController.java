package com.futengwl.controller.member;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.futengwl.entity.Member;
import com.futengwl.entity.Rebate;
import com.futengwl.security.CurrentUser;
import com.futengwl.service.RebateService;

@Controller("memberRebateController")
@RequestMapping("/member/rebate")
public class RebateController extends BaseController {

	@Inject
	private RebateService rebateService;

	@GetMapping("/check")
	@ResponseBody
	public String check(String mobile, String linkmanTelephone, ModelMap model) {
		
		int result = 0;
		if(rebateService.checkIsBuy(mobile) > 0) {
			result = 1;
			if(!rebateService.checkLinkman(linkmanTelephone)) {				
				result = 2;
			}
		}
		model.put("result", result);
		return JSONArray.toJSONString(result);
	}

	@GetMapping("/rebate")
	public String rebate(ModelMap model, @CurrentUser Member user, Rebate rebate) {
		rebate.setMobile(user.getMobile());
		rebate.setName(user.getName());
		model.addAttribute("rebate",rebate);
		return "/member/rebate/rebate";
	}

	@PostMapping("/save")
	@ResponseBody
	public String saveRebate(String username, String mobile, String eataddress, String linkman, String linkmanTelephone, String recommendDiningRoom, Rebate rebate) {
		rebate.setName(username);
		rebate.setMobile(mobile);
		rebate.setRecommendDiningRoom(recommendDiningRoom);
		rebate.setEataddress(eataddress);
		rebate.setLinkman(linkman);
		rebate.setLinkmanTelephone(linkmanTelephone);
		rebate.setStatus(Rebate.Status.PENDING_REVIEW);
		rebate = rebateService.save(rebate);
		if(rebate.getId() != null) {
			return "true";			
		} else {
			return "false";
		}
	}
}
