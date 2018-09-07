/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: qEi/chHW1Ko5wwDFaYQNUtrnKi6qSH9L
 */
package com.futengwl.controller.shop;

import net.sf.json.JSONArray;
import com.futengwl.entity.ProductCategory;
import com.futengwl.service.ProductCategoryService;
import com.futengwl.util.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


/**
 * Controller - 商品分类
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("forOuterSystemController")
@RequestMapping("/api")
public class ProductCategoryControllerForOuterSystem extends BaseController {

	@Inject
	private ProductCategoryService productCategoryService;

	/**
	 * 外部系统调用
	 */

	@GetMapping("/productCategory")
	public @ResponseBody
    List getProductCategory() {
		ArrayList<ProductCategory> list = (ArrayList<ProductCategory> )productCategoryService.findTree();
		ArrayList returnList= new ArrayList<com.futengwl.dto.ProductCategory>();

		for(ProductCategory pc: list){
            com.futengwl.dto.ProductCategory returnPc = new com.futengwl.dto.ProductCategory();
            returnPc.setId(pc.getId());
            returnPc.setName(pc.getName());
            returnPc.setGrade(pc.getGrade());
            returnPc.setTreePath(pc.getTreePath());
            returnList.add(returnPc);
        }
        return returnList;
	}

}