/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: wIp0jISVPv5xBY+wxPqPl9ZBS7iEhblG
 */
package com.futengwl.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.futengwl.entity.SpecificationItem;
import com.futengwl.service.SpecificationItemService;

/**
 * Service - 规格项
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class SpecificationItemServiceImpl implements SpecificationItemService {

	@Override
	public void filter(List<SpecificationItem> specificationItems) {
		CollectionUtils.filter(specificationItems, new Predicate() {
			public boolean evaluate(Object object) {
				SpecificationItem specificationItem = (SpecificationItem) object;
				if (specificationItem == null || StringUtils.isEmpty(specificationItem.getName())) {
					return false;
				}
				CollectionUtils.filter(specificationItem.getEntries(), new Predicate() {
					private Set<Integer> idSet = new HashSet<>();
					private Set<String> valueSet = new HashSet<>();

					public boolean evaluate(Object object) {
						SpecificationItem.Entry entry = (SpecificationItem.Entry) object;
						return entry != null && entry.getId() != null && StringUtils.isNotEmpty(entry.getValue()) && entry.getIsSelected() != null && idSet.add(entry.getId()) && valueSet.add(entry.getValue());
					}
				});
				return CollectionUtils.isNotEmpty(specificationItem.getEntries()) && specificationItem.isSelected();
			}
		});
	}

}