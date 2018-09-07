/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: KrT/p6+U64UJMBcUNigYnK012jYdY0Z1
 */
package com.futengwl.plugin;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.futengwl.entity.Promotion;

/**
 * Plugin - 满减促销
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Component("fullReductionPromotionPlugin")
public class FullReductionPromotionPlugin extends PromotionPlugin {

	/**
	 * ID
	 */
	public static final String ID = "fullReductionPromotionPlugin";

	@Override
	public String getName() {
		return "满减促销";
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
		return "/admin/plugin/full_reduction_promotion/install";
	}

	@Override
	public String getUninstallUrl() {
		return "/admin/plugin/full_reduction_promotion/uninstall";
	}

	@Override
	public String getSettingUrl() {
		return "/admin/plugin/full_reduction_promotion/setting";
	}

	@Override
	public String getPriceExpression(Promotion promotion, Boolean useAmountPromotion, Boolean useNumberPromotion) {
		if (useAmountPromotion != null && useAmountPromotion) {
			BigDecimal conditionsAmoun = promotion.getConditionsAmount();
			BigDecimal creditAmount = promotion.getCreditAmount();
			if (conditionsAmoun != null && creditAmount != null && conditionsAmoun.compareTo(BigDecimal.ZERO) > 0 && creditAmount.compareTo(BigDecimal.ZERO) > 0) {
				return "price-((price/" + String.valueOf(conditionsAmoun) + ") as int) *" + String.valueOf(creditAmount);
			}
		} else if (useNumberPromotion != null && useNumberPromotion) {
			Integer conditionsNumber = promotion.getConditionsNumber();
			Integer creditNumber = promotion.getCreditNumber();
			if (conditionsNumber != null && creditNumber != null && conditionsNumber > 0 && creditNumber > 0) {
				return "price-(quantity.intdiv(" + conditionsNumber + "))*" + "(" + creditNumber + "*" + "(price/quantity)" + ")";
			}
		}
		return StringUtils.EMPTY;
	}

}