/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: KV8jpuug+tmAzxDxKGwf2jkoXBMyslDP
 */
package com.futengwl.controller.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.futengwl.entity.Area;
import com.futengwl.service.AreaService;

/**
 * Controller - 地区
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Controller("commonAreaController")
@RequestMapping("/common/area")
public class AreaController {

	@Inject
	private AreaService areaService;

	/**
	 * 地区
	 */
	@GetMapping
	public @ResponseBody List<Map<String, Object>> index(Long parentId) {
		List<Map<String, Object>> data = new ArrayList<>();
		Area parent = areaService.find(parentId);
		Collection<Area> areas = parent != null ? parent.getChildren() : areaService.findRoots();
		for (Area area : areas) {
			Map<String, Object> item = new HashMap<>();
			item.put("name", area.getName());
			item.put("value", area.getId());
			data.add(item);
		}
		return data;
	}

	/**
	 * 根据gps location 获取地区
	 */
	@GetMapping("/location")
	public @ResponseBody Map<String, Object> getAreaByGpsLocation(String  location) {
		String areaName=areaService.getAreaNameByGPSLocation(location);
		if(StringUtils.isNotEmpty(areaName)){
			Area area=areaService.findOne(areaName);
			if(area!=null){
				Map result= new HashMap<String,Object>();
				result.put("areaId",area.getId());
				result.put("areaTreePath",area.getTreePath());
				return result;
			}
		}
		return null;
	}

}