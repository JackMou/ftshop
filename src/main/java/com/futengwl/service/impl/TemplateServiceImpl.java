/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: g2iVeERbV3H3V7KHyoOEuJRfN/ccQYw4
 */
package com.futengwl.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.futengwl.TemplateConfig;
import com.futengwl.service.TemplateService;
import com.futengwl.util.SystemUtils;

/**
 * Service - 模板
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class TemplateServiceImpl implements TemplateService {

	@Value("${template.loader_path}")
	private String templateLoaderPath;

	@Inject
	private ServletContext servletContext;

	@Override
	public String read(String templateConfigId) {
		Assert.hasText(templateConfigId, "[Assertion failed] - templateConfigId must have text; it must not be null, empty, or blank");

		TemplateConfig templateConfig = SystemUtils.getTemplateConfig(templateConfigId);
		return read(templateConfig);
	}

	@Override
	public String read(TemplateConfig templateConfig) {
		Assert.notNull(templateConfig, "[Assertion failed] - templateConfig is required; it must not be null");

		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(servletContext.getResourceAsStream(templateLoaderPath + templateConfig.getTemplatePath()));
			return IOUtils.toString(inputStream, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}

	@Override
	public void write(String templateConfigId, String content) {
		Assert.hasText(templateConfigId, "[Assertion failed] - templateConfigId must have text; it must not be null, empty, or blank");

		TemplateConfig templateConfig = SystemUtils.getTemplateConfig(templateConfigId);
		write(templateConfig, content);
	}

	@Override
	public void write(TemplateConfig templateConfig, String content) {
		Assert.notNull(templateConfig, "[Assertion failed] - templateConfig is required; it must not be null");

		try {
			File templateFile = new File(servletContext.getRealPath("/"), templateLoaderPath + templateConfig.getTemplatePath());
			FileUtils.writeStringToFile(templateFile, content, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}