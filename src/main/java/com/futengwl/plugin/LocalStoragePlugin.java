/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: ncZvrFJFFMqetmY0Du+KGmNonYyjqutk
 */
package com.futengwl.plugin;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import com.futengwl.Setting;
import com.futengwl.util.SystemUtils;

/**
 * Plugin - 本地文件存储
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Component("localStoragePlugin")
public class LocalStoragePlugin extends StoragePlugin {

	@Inject
	private ServletContext servletContext;

	@Override
	public String getName() {
		return "本地文件存储";
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
	public String getSiteUrl() {
		return "http://www.futengwl.com";
	}

	@Override
	public String getInstallUrl() {
		return null;
	}

	@Override
	public String getUninstallUrl() {
		return null;
	}

	@Override
	public String getSettingUrl() {
		return "/admin/plugin/local_storage/setting";
	}

	@Override
	public void upload(String path, File file, String contentType) {
		File destFile = new File(servletContext.getRealPath("/"), path);
		try {
			FileUtils.moveFile(file, destFile);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public String getUrl(String path) {
		Setting setting = SystemUtils.getSetting();
		return setting.getSiteUrl() + path;
	}

}