/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: L0jGQYSgi+vbusKxJNYpuFHPkAw5AOEf
 */
package com.futengwl.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import com.futengwl.util.JsonUtils;
import com.futengwl.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.futengwl.dao.AreaDao;
import com.futengwl.entity.Area;
import com.futengwl.service.AreaService;

/**
 * Service - 地区
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class AreaServiceImpl extends BaseServiceImpl<Area, Long> implements AreaService {

	@Inject
	private AreaDao areaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Area> findRoots() {
		return areaDao.findRoots(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Area> findRoots(Integer count) {
		return areaDao.findRoots(count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Area> findParents(Area area, boolean recursive, Integer count) {
		return areaDao.findParents(area, recursive, count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Area> findChildren(Area area, boolean recursive, Integer count) {
		return areaDao.findChildren(area, recursive, count);
	}

	@Override
	@Transactional(readOnly = true)
	public Area findOne(String fullName){
		return areaDao.findOne(fullName);
	}

    @Value("${geocoder.service.url}")
    private String serviceURL;
    @Value("${geocoder.service.key}")
    private String key;
    @Override
    public String getAreaNameByGPSLocation(String location) {

        HashMap params = new HashMap<String,String>();
        params.put("key",key);
        params.put("location",location);
        String resultJson = WebUtils.get(serviceURL,params);
        System.out.println(resultJson);
        HashMap<String,Object> resultMap = JsonUtils.toObject(resultJson,HashMap.class);

        if(0==(int)resultMap.get("status")){
            resultMap=(HashMap<String,Object>)resultMap.get("result");
            if(null!= resultMap) {
                resultMap = (HashMap<String, Object>) resultMap.get("address_component");
                if(null!= resultMap) {
                    String province = (String) resultMap.get("province");
                    String city = (String) resultMap.get("city");
                    String district = (String) resultMap.get("district");
                    return province == city ? city + district : province + city + district;
                }
            }
        }
        return null;
    }

    @Override
	@Transactional
	@CacheEvict(value = "areaPage", allEntries = true)
	public Area save(Area area) {
		Assert.notNull(area, "[Assertion failed] - area is required; it must not be null");

		setValue(area);
		return super.save(area);
	}

	@Override
	@Transactional
	@CacheEvict(value = "areaPage", allEntries = true)
	public Area update(Area area) {
		Assert.notNull(area, "[Assertion failed] - area is required; it must not be null");

		setValue(area);
		for (Area children : areaDao.findChildren(area, true, null)) {
			setValue(children);
		}
		return super.update(area);
	}

	@Override
	@Transactional
	@CacheEvict(value = "areaPage", allEntries = true)
	public Area update(Area area, String... ignoreProperties) {
		return super.update(area, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "areaPage", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "areaPage", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "areaPage", allEntries = true)
	public void delete(Area area) {
		super.delete(area);
	}

	/**
	 * 设置值
	 * 
	 * @param area
	 *            地区
	 */
	private void setValue(Area area) {
		if (area == null) {
			return;
		}
		Area parent = area.getParent();
		if (parent != null) {
			area.setFullName(parent.getFullName() + area.getName());
			area.setTreePath(parent.getTreePath() + parent.getId() + Area.TREE_PATH_SEPARATOR);
		} else {
			area.setFullName(area.getName());
			area.setTreePath(Area.TREE_PATH_SEPARATOR);
		}
		area.setGrade(area.getParentIds().length);
	}

}