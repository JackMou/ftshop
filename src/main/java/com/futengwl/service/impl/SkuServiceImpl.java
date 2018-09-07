/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: BuNmsSPlWonQFvBkEbN4tguQ7vlApCCK
 */
package com.futengwl.service.impl;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.LockModeType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.futengwl.dao.SkuDao;
import com.futengwl.dao.StockLogDao;
import com.futengwl.entity.Product;
import com.futengwl.entity.Sku;
import com.futengwl.entity.StockLog;
import com.futengwl.entity.Store;
import com.futengwl.service.SkuService;

/**
 * Service - SKU
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class SkuServiceImpl extends BaseServiceImpl<Sku, Long> implements SkuService {

	@Inject
	private SkuDao skuDao;
	@Inject
	private StockLogDao stockLogDao;

	@Override
	@Transactional(readOnly = true)
	public boolean snExists(String sn) {
		return skuDao.exists("sn", StringUtils.lowerCase(sn));
	}

	@Override
	@Transactional(readOnly = true)
	public Sku findBySn(String sn) {
		return skuDao.find("sn", StringUtils.lowerCase(sn));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Sku> search(Store store, Product.Type type, String keyword, Set<Sku> excludes, Integer count) {
		return skuDao.search(store, type, keyword, excludes, count);
	}

	@Override
	public void addStock(Sku sku, int amount, StockLog.Type type, String memo) {
		Assert.notNull(sku, "[Assertion failed] - sku is required; it must not be null");
		Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");

		if (amount == 0) {
			return;
		}

		if (!LockModeType.PESSIMISTIC_WRITE.equals(skuDao.getLockMode(sku))) {
			skuDao.flush();
			skuDao.refresh(sku, LockModeType.PESSIMISTIC_WRITE);
		}

		Assert.notNull(sku.getStock(), "[Assertion failed] - sku stock is required; it must not be null");
		Assert.state(sku.getStock() + amount >= 0, "[Assertion failed] - sku stock must be equal or greater than 0");

		sku.setStock(sku.getStock() + amount);
		skuDao.flush();

		StockLog stockLog = new StockLog();
		stockLog.setType(type);
		stockLog.setInQuantity(amount > 0 ? amount : 0);
		stockLog.setOutQuantity(amount < 0 ? Math.abs(amount) : 0);
		stockLog.setStock(sku.getStock());
		stockLog.setMemo(memo);
		stockLog.setSku(sku);
		stockLogDao.persist(stockLog);
	}

	@Override
	public void addAllocatedStock(Sku sku, int amount) {
		Assert.notNull(sku, "[Assertion failed] - sku is required; it must not be null");

		if (amount == 0) {
			return;
		}

		if (!LockModeType.PESSIMISTIC_WRITE.equals(skuDao.getLockMode(sku))) {
			skuDao.flush();
			skuDao.refresh(sku, LockModeType.PESSIMISTIC_WRITE);
		}

		Assert.notNull(sku.getAllocatedStock(), "[Assertion failed] - sku allocatedStock is required; it must not be null");
		Assert.state(sku.getAllocatedStock() + amount >= 0, "[Assertion failed] - sku allocatedStock must be equal or greater than 0");

		sku.setAllocatedStock(sku.getAllocatedStock() + amount);
		skuDao.flush();
	}

	@Override
	@Transactional(readOnly = true)
	public void filter(List<Sku> skus) {
		CollectionUtils.filter(skus, new Predicate() {
			public boolean evaluate(Object object) {
				Sku sku = (Sku) object;
				return sku != null && sku.getStock() != null;
			}
		});
	}

}