/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: HToEl7dhDoAoQJ3Ruk12xFigTQ5/T7sN
 */
package com.futengwl.plugin;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.futengwl.entity.Promotion;

/**
 * Plugin - 折扣促销
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Component("discountPromotionPlugin")
public class DiscountPromotionPlugin extends PromotionPlugin {

	/**
	 * ID
	 */
	public static final String ID = "discountPromotionPlugin";

	@Override
	public String getName() {
		return "折扣促销";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getAuthor() {
		return "FTSHOP";
	}

	@Override
	public String getInstallUrl() {
		return "/admin/plugin/discount_promotion/install";
	}

	@Override
	public String getUninstallUrl() {
		return "/admin/plugin/discount_promotion/uninstall";
	}

	@Override
	public String getSettingUrl() {
		return "/admin/plugin/discount_promotion/setting";
	}

	@Override
	public String getPriceExpression(Promotion promotion, Boolean useAmountPromotion, Boolean useNumberPromotion) {
		if (promotion.getDiscount() == null) {
			return StringUtils.EMPTY;
		}
		if (promotion.getDiscount() < 1 && promotion.getDiscount() > 0) {
			return "price*" + String.valueOf(promotion.getDiscount());
		} else {
			return "price-" + String.valueOf(promotion.getDiscount());
		}
	}

}