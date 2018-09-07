/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: RM5VNx4cAIfSbiiixAaYRJEzWX5nbR7G
 */
package com.futengwl.service;

import com.futengwl.entity.PluginConfig;

/**
 * Service - 插件配置
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface PluginConfigService extends BaseService<PluginConfig, Long> {

	/**
	 * 判断插件ID是否存在
	 * 
	 * @param pluginId
	 *            插件ID
	 * @return 插件ID是否存在
	 */
	boolean pluginIdExists(String pluginId);

	/**
	 * 根据插件ID查找插件配置
	 * 
	 * @param pluginId
	 *            插件ID
	 * @return 插件配置，若不存在则返回null
	 */
	PluginConfig findByPluginId(String pluginId);

	/**
	 * 根据插件ID删除插件配置
	 * 
	 * @param pluginId
	 *            插件ID
	 */
	void deleteByPluginId(String pluginId);

}