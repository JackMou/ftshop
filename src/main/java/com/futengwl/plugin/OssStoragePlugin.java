/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: qjD5+pjI/BcEBI7sg7myMzo3SoRieAgU
 */
package com.futengwl.plugin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;

import com.futengwl.entity.PluginConfig;

/**
 * Plugin - 阿里云存储
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Component("ossStoragePlugin")
public class OssStoragePlugin extends StoragePlugin {

	@Override
	public String getName() {
		return "阿里云存储";
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
		return "/admin/plugin/oss_storage/install";
	}

	@Override
	public String getUninstallUrl() {
		return "/admin/plugin/oss_storage/uninstall";
	}

	@Override
	public String getSettingUrl() {
		return "/admin/plugin/oss_storage/setting";
	}

	@Override
	public void upload(String path, File file, String contentType) {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String endpoint = pluginConfig.getAttribute("endpoint");
			String accessId = pluginConfig.getAttribute("accessId");
			String accessKey = pluginConfig.getAttribute("accessKey");
			String bucketName = pluginConfig.getAttribute("bucketName");
			InputStream inputStream = null;
			try {
				inputStream = new BufferedInputStream(new FileInputStream(file));
				OSSClient ossClient = new OSSClient(endpoint, accessId, accessKey);
				ObjectMetadata objectMetadata = new ObjectMetadata();
				objectMetadata.setContentType(contentType);
				objectMetadata.setContentLength(file.length());
				ossClient.putObject(bucketName, StringUtils.removeStart(path, "/"), inputStream, objectMetadata);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e.getMessage(), e);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
	}

	@Override
	public String getUrl(String path) {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String urlPrefix = pluginConfig.getAttribute("urlPrefix");
			return urlPrefix + path;
		}
		return null;
	}

}