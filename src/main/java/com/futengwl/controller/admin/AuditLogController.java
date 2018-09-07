/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 89I8Dkz72sV4oezp+BvukpYatolKt0xj
 */
package com.futengwl.controller.admin;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futengwl.Pageable;
import com.futengwl.Results;
import com.futengwl.service.AuditLogService;

/**
 * Controller - 审计日志
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("adminAuditLogController")
@RequestMapping("/admin/audit_log")
public class AuditLogController extends BaseController {

	@Inject
	private AuditLogService auditLogService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", auditLogService.findPage(pageable));
		return "admin/audit_log/list";
	}

	/**
	 * 查看
	 */
	@GetMapping("/view")
	public String view(Long id, ModelMap model) {
		model.addAttribute("auditLog", auditLogService.find(id));
		return "admin/audit_log/view";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public ResponseEntity<?> delete(Long[] ids) {
		auditLogService.delete(ids);
		return Results.OK;
	}

	/**
	 * 清空
	 */
	@PostMapping("/clear")
	public ResponseEntity<?> clear() {
		auditLogService.clear();
		return Results.OK;
	}

}