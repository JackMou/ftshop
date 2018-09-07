package com.futengwl.controller.admin;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.futengwl.ExcelView;
import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Rebate;
import com.futengwl.service.RebateService;

@Controller("adminRebateController")
@RequestMapping("/admin/rebate")
public class RebateController extends BaseController {

	@Inject
	RebateService rebateService;

	@GetMapping("/list")
	public String list(Date beginDate, Date endDate, Pageable pageable, ModelMap model) {
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("page", rebateService.findPage(beginDate, endDate, pageable));
		return "/admin/rebate/list";
	}

	/**
	 * 下载返利推荐信息
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param rebate
	 * @param model
	 * @return
	 */
	@GetMapping("/download")
	public ModelAndView listDownload(Date beginDate, Date endDate, Rebate rebate, Pageable pageable, ModelMap model) {

		if (beginDate != null) {
			model.addAttribute("beginDate", DateFormatUtils.format(beginDate, "yyyyMMddHHmmss"));
		}
		if (endDate != null) {
			model.addAttribute("endDate", DateFormatUtils.format(endDate, "yyyyMMddHHmmss"));
		} else {
			model.addAttribute("endDate", DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
		}
		
		Page page = rebateService.findPage(beginDate, endDate, null);
		List rebates = page.getContent();
		for (int i = page.getPageNumber() + 1; i <= page.getTotalPages(); i++) {
			pageable.setPageNumber(i);
			page = rebateService.findPage(beginDate, endDate, pageable);
			rebates.addAll(page.getContent());
		}
		model.addAttribute("rebate", rebates);
		String filename = "rebate_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls";
		return new ModelAndView(new ExcelView("/admin/rebate/download.xls", filename), model);
	}
}
