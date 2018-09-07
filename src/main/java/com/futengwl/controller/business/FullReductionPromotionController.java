/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: VNgSiRzmUmqPScH7ECkZ0ybux7Pqzc+z
 */
package com.futengwl.controller.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.futengwl.Pageable;
import com.futengwl.Results;
import com.futengwl.entity.Business;
import com.futengwl.entity.Product;
import com.futengwl.entity.Promotion;
import com.futengwl.entity.PromotionPluginSvc;
import com.futengwl.entity.Sku;
import com.futengwl.entity.Store;
import com.futengwl.exception.UnauthorizedException;
import com.futengwl.plugin.FullReductionPromotionPlugin;
import com.futengwl.plugin.PaymentPlugin;
import com.futengwl.plugin.PromotionPlugin;
import com.futengwl.security.CurrentStore;
import com.futengwl.security.CurrentUser;
import com.futengwl.service.CouponService;
import com.futengwl.service.MemberRankService;
import com.futengwl.service.PluginService;
import com.futengwl.service.PromotionService;
import com.futengwl.service.SkuService;
import com.futengwl.service.StoreService;
import com.futengwl.service.SvcService;
import com.futengwl.util.WebUtils;

/**
 * Controller - 满减促销
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("businessFullReductionPromotionController")
@RequestMapping("/business/full_reduction_promotion")
public class FullReductionPromotionController extends BaseController {

	@Inject
	private PromotionService promotionService;
	@Inject
	private MemberRankService memberRankService;
	@Inject
	private SkuService skuService;
	@Inject
	private CouponService couponService;
	@Inject
	private StoreService storeService;
	@Inject
	private PluginService pluginService;
	@Inject
	private SvcService svcService;

	/**
	 * 添加属性
	 */
	@ModelAttribute
	public void populateModel(Long promotionId, @CurrentStore Store currentStore, ModelMap model) {
		Promotion promotion = promotionService.find(promotionId);
		if (promotion != null && !currentStore.equals(promotion.getStore())) {
			throw new UnauthorizedException();
		}
		model.addAttribute("promotion", promotion);
	}

	/**
	 * 计算
	 */
	@GetMapping("/calculate")
	public ResponseEntity<?> calculate(String paymentPluginId, Integer months, Boolean useBalance) {
		Map<String, Object> data = new HashMap<>();
		PromotionPlugin promotionPlugin = pluginService.getPromotionPlugin(FullReductionPromotionPlugin.ID);
		if (promotionPlugin == null || !promotionPlugin.getIsEnabled()) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (months == null || months <= 0) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		BigDecimal amount = promotionPlugin.getPrice().multiply(new BigDecimal(months));
		if (BooleanUtils.isTrue(useBalance)) {
			data.put("fee", BigDecimal.ZERO);
			data.put("amount", amount);
			data.put("useBalance", true);
		} else {
			PaymentPlugin paymentPlugin = pluginService.getPaymentPlugin(paymentPluginId);
			if (paymentPlugin == null || !paymentPlugin.getIsEnabled()) {
				return Results.UNPROCESSABLE_ENTITY;
			}
			data.put("fee", paymentPlugin.calculateFee(amount));
			data.put("amount", paymentPlugin.calculateAmount(amount));
			data.put("useBalance", false);
		}
		return ResponseEntity.ok(data);
	}

	/**
	 * 到期日期
	 */
	@GetMapping("/end_date")
	public @ResponseBody Map<String, Object> endDate(@CurrentStore Store currentStore) {
		Map<String, Object> data = new HashMap<>();
		data.put("endDate", currentStore.getFullReductionPromotionEndDate());
		return data;
	}

	/**
	 * 购买
	 */
	@GetMapping("/buy")
	public String buy(@CurrentStore Store currentStore, ModelMap model) {
		if (currentStore.getType().equals(Store.Type.SELF) && currentStore.getFullReductionPromotionEndDate() == null) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		PromotionPlugin promotionPlugin = pluginService.getPromotionPlugin(FullReductionPromotionPlugin.ID);
		if (promotionPlugin == null || !promotionPlugin.getIsEnabled()) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		List<PaymentPlugin> paymentPlugins = pluginService.getActivePaymentPlugins(WebUtils.getRequest());
		if (CollectionUtils.isNotEmpty(paymentPlugins)) {
			model.addAttribute("defaultPaymentPlugin", paymentPlugins.get(0));
			model.addAttribute("paymentPlugins", paymentPlugins);
		}
		model.addAttribute("promotionPlugin", promotionPlugin);
		return "business/full_reduction_promotion/buy";
	}

	/**
	 * 购买
	 */
	@PostMapping("/buy")
	public ResponseEntity<?> buy(Integer months, Boolean useBalance, @CurrentStore Store currentStore, @CurrentUser Business currentUser) {
		Map<String, Object> data = new HashMap<>();
		PromotionPlugin promotionPlugin = pluginService.getPromotionPlugin(FullReductionPromotionPlugin.ID);
		if (currentStore.getType().equals(Store.Type.SELF) && currentStore.getFullReductionPromotionEndDate() == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (promotionPlugin == null || !promotionPlugin.getIsEnabled()) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (months == null || months <= 0) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		int days = months * 30;
		BigDecimal amount = promotionPlugin.getPrice().multiply(new BigDecimal(months));
		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			if (BooleanUtils.isTrue(useBalance)) {
				if (currentUser.getAvailableBalance().compareTo(amount) < 0) {
					return Results.unprocessableEntity("business.fullReductionPromotion.insufficientBalance");
				}
				storeService.buy(currentStore, promotionPlugin, months);
			} else {
				PromotionPluginSvc promotionPluginSvc = new PromotionPluginSvc();
				promotionPluginSvc.setAmount(amount);
				promotionPluginSvc.setDurationDays(days);
				promotionPluginSvc.setStore(currentStore);
				promotionPluginSvc.setPromotionPluginId(FullReductionPromotionPlugin.ID);
				svcService.save(promotionPluginSvc);
				data.put("promotionPluginSvcSn", promotionPluginSvc.getSn());
			}
		} else {
			storeService.addFullReductionPromotionEndDays(currentStore, days);
		}
		return ResponseEntity.ok(data);
	}

	/**
	 * 赠品选择
	 */
	@GetMapping("/gift_select")
	public @ResponseBody List<Map<String, Object>> giftSelect(String keyword, Long[] excludeIds, @CurrentUser Business currentUser) {
		List<Map<String, Object>> data = new ArrayList<>();
		if (StringUtils.isEmpty(keyword)) {
			return data;
		}
		Set<Sku> excludes = new HashSet<>(skuService.findList(excludeIds));
		List<Sku> skus = skuService.search(currentUser.getStore(), Product.Type.GIFT, keyword, excludes, null);
		for (Sku sku : skus) {
			Map<String, Object> item = new HashMap<>();
			item.put("id", sku.getId());
			item.put("sn", sku.getSn());
			item.put("name", sku.getName());
			item.put("specifications", sku.getSpecifications());
			item.put("path", sku.getPath());
			data.add(item);
		}
		return data;
	}

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(@CurrentStore Store currentStore, ModelMap model) {
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("coupons", couponService.findList(currentStore));
		return "business/full_reduction_promotion/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public ResponseEntity<?> save(@ModelAttribute("promotionForm") Promotion promotionForm, Boolean useAmountPromotion, Boolean useNumberPromotion, Long[] memberRankIds, Long[] couponIds, Long[] giftIds, @CurrentStore Store currentStore) {
		promotionForm.setType(Promotion.Type.FULL_REDUCTION);
		promotionForm.setStore(currentStore);
		promotionForm.setMemberRanks(new HashSet<>(memberRankService.findList(memberRankIds)));
		promotionForm.setCoupons(new HashSet<>(couponService.findList(couponIds)));
		if (ArrayUtils.isNotEmpty(giftIds)) {
			List<Sku> gifts = skuService.findList(giftIds);
			CollectionUtils.filter(gifts, new Predicate() {
				public boolean evaluate(Object object) {
					Sku gift = (Sku) object;
					return gift != null && Product.Type.GIFT.equals(gift.getType());
				}
			});
			promotionForm.setGifts(new HashSet<>(gifts));
		} else {
			promotionForm.setGifts(null);
		}
		if (!isValid(promotionForm)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (promotionForm.getBeginDate() != null && promotionForm.getEndDate() != null && promotionForm.getBeginDate().after(promotionForm.getEndDate())) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (promotionForm.getMinimumQuantity() != null && promotionForm.getMaximumQuantity() != null && promotionForm.getMinimumQuantity() > promotionForm.getMaximumQuantity()) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (promotionForm.getMinimumPrice() != null && promotionForm.getMaximumPrice() != null && promotionForm.getMinimumPrice().compareTo(promotionForm.getMaximumPrice()) > 0) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		PromotionPlugin promotionPlugin = pluginService.getPromotionPlugin(FullReductionPromotionPlugin.ID);
		String priceExpression = promotionPlugin.getPriceExpression(promotionForm, useAmountPromotion, useNumberPromotion);
		if (StringUtils.isNotEmpty(priceExpression) && !promotionService.isValidPriceExpression(priceExpression)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		promotionForm.setPriceExpression(priceExpression);
		promotionForm.setProducts(null);
		promotionForm.setProductCategories(null);
		promotionService.save(promotionForm);

		return Results.OK;
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(@ModelAttribute(binding = false) Promotion promotion, @CurrentStore Store currentStore, ModelMap model) {
		if (promotion == null) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}

		model.addAttribute("promotion", promotion);
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("coupons", couponService.findList(currentStore));
		return "business/full_reduction_promotion/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(@ModelAttribute("promotionForm") Promotion promotionForm, @ModelAttribute(binding = false) Promotion promotion, Boolean useAmountPromotion, Boolean useNumberPromotion, Long[] memberRankIds, Long[] couponIds, Long[] giftIds) {
		if (promotion == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}

		promotionForm.setMemberRanks(new HashSet<>(memberRankService.findList(memberRankIds)));
		promotionForm.setCoupons(new HashSet<>(couponService.findList(couponIds)));
		if (ArrayUtils.isNotEmpty(giftIds)) {
			List<Sku> gifts = skuService.findList(giftIds);
			CollectionUtils.filter(gifts, new Predicate() {
				public boolean evaluate(Object object) {
					Sku gift = (Sku) object;
					return gift != null && Product.Type.GIFT.equals(gift.getType());
				}
			});
			promotionForm.setGifts(new HashSet<>(gifts));
		} else {
			promotionForm.setGifts(null);
		}
		if (promotionForm.getBeginDate() != null && promotionForm.getEndDate() != null && promotionForm.getBeginDate().after(promotionForm.getEndDate())) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (promotionForm.getMinimumQuantity() != null && promotionForm.getMaximumQuantity() != null && promotionForm.getMinimumQuantity() > promotionForm.getMaximumQuantity()) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (promotionForm.getMinimumPrice() != null && promotionForm.getMaximumPrice() != null && promotionForm.getMinimumPrice().compareTo(promotionForm.getMaximumPrice()) > 0) {
			return Results.UNPROCESSABLE_ENTITY;
		}

		PromotionPlugin promotionPlugin = pluginService.getPromotionPlugin(FullReductionPromotionPlugin.ID);
		String priceExpression = promotionPlugin.getPriceExpression(promotionForm, useAmountPromotion, useNumberPromotion);
		if (StringUtils.isNotEmpty(priceExpression) && !promotionService.isValidPriceExpression(priceExpression)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (useAmountPromotion != null && useAmountPromotion) {
			promotionForm.setConditionsNumber(null);
			promotionForm.setCreditNumber(null);
		} else if (useNumberPromotion != null && useNumberPromotion) {
			promotionForm.setConditionsAmount(null);
			promotionForm.setCreditAmount(null);
		} else {
			promotionForm.setConditionsNumber(null);
			promotionForm.setCreditNumber(null);
			promotionForm.setConditionsAmount(null);
			promotionForm.setCreditAmount(null);
		}
		promotionForm.setPriceExpression(priceExpression);

		BeanUtils.copyProperties(promotionForm, promotion, "id", "type", "store", "product", "productCategories");
		promotionService.update(promotion);

		return Results.OK;
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, @CurrentStore Store currentStore, ModelMap model) {
		PromotionPlugin promotionPlugin = pluginService.getPromotionPlugin(FullReductionPromotionPlugin.ID);
		model.addAttribute("isEnabled", promotionPlugin.getIsEnabled());
		model.addAttribute("page", promotionService.findPage(currentStore, Promotion.Type.FULL_REDUCTION, pageable));
		return "business/full_reduction_promotion/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public ResponseEntity<?> delete(Long[] ids, @CurrentStore Store currentStore) {
		for (Long id : ids) {
			Promotion promotion = promotionService.find(id);
			if (promotion == null) {
				return Results.UNPROCESSABLE_ENTITY;
			}
			if (!currentStore.equals(promotion.getStore())) {
				return Results.UNPROCESSABLE_ENTITY;
			}
		}
		promotionService.delete(ids);
		return Results.OK;
	}

}