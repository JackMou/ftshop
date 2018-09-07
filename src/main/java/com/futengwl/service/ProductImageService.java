/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: QHQeVORtW5HIUoGaF+060+hb+sBoFsPW
 */
package com.futengwl.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.futengwl.entity.ProductImage;

/**
 * Service - 商品图片
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface ProductImageService {

	/**
	 * 商品图片过滤
	 * 
	 * @param productImages
	 *            商品图片
	 */
	void filter(List<ProductImage> productImages);

	/**
	 * 生成商品图片
	 * 
	 * @param multipartFile
	 *            文件
	 * @return 商品图片
	 */
	ProductImage generate(MultipartFile multipartFile);

}