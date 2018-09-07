/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: qEi/chHW1Ko5wwDFaYQNUtrnKi6qSH9L
 */
package com.futengwl.controller.shop;

import javax.inject.Inject;

import com.futengwl.service.StoreProductCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futengwl.service.ProductCategoryService;

/**
 * Controller - 商品分类
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("shopProductCategoryController")
@RequestMapping("/product_category")
public class ProductCategoryController extends BaseController {

	@Inject
	private ProductCategoryService productCategoryService;
	@Inject
	private StoreProductCategoryService storeProductCategoryService;

	/**
	 * 首页
	 */
	@GetMapping
	public String index(ModelMap model,Long storeId) {
		if(storeId==null) {
			model.addAttribute("rootProductCategories", productCategoryService.findRoots());
		}else{
            model.addAttribute("storeId", storeId);
			model.addAttribute("rootProductCategories", storeProductCategoryService.findRoots(storeId));
		}
		return "shop/product_category/index";
	}

}