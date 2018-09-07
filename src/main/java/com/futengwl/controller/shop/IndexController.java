/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: Httv16E4NasIj1s7g0kzZxshUYytSZZk
 */
package com.futengwl.controller.shop;

import com.alibaba.fastjson.JSONObject;
import com.futengwl.util.WebUtils;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 首页
 *
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("shopIndexController")
@RequestMapping("/")
public class IndexController extends BaseController {
    /**
     * 首页
     */

    @GetMapping("/")
    public String index(ModelMap model, HttpServletRequest request) {
        String bpId = request.getParameter("bpId");
        if (!StringUtils.isEmpty(bpId)) {
            Map<String, Object> params = new HashMap<>();
            params.put("bpId", bpId);
            String from = WebUtils.postForm("http://myyadmin.development/api/oauth/isDalian", null, params);
            JSONObject obj = JSONObject.parseObject(from);
            boolean flag = false;
            if (obj.containsKey("code")) {
                flag = obj.getBoolean("body");
            }
            if(true==flag) {
                model.addAttribute("redirectURL", "/store/10001?bpId=" + bpId);
            }else{
                model.addAttribute("redirectURL", "/store/5?bpId=" + bpId);
                flag=true;
            }
            model.addAttribute("flag", flag);
        }
        return "shop/index";
    }

}