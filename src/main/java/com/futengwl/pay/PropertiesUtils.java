package com.futengwl.pay;

import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    // 工具类不需要实例化
    private PropertiesUtils() {
    }


    public static Properties loadProperties(final String propertieFileName) throws IOException {
        Assert.hasText(propertieFileName, "Parameter 'propertieFileName' must not be null or empty");

        InputStream inStream = null;
        try {
            inStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertieFileName);

            Properties properties = new Properties();
            properties.load(inStream);
            return properties;
        } finally {
            if (inStream != null) {
                inStream.close();
            }
        }
    }


    public static void main(String[] args) throws Exception {
        try {
            Properties pro = loadProperties("com/prs/framework/core/web/controller/springmvc/DispatchAction.properties");

            String mappingClassName = pro.getProperty("com.prs.framework.core.web.controller.springmvc.ActionHandlerMapping");
            System.out.println(mappingClassName);

            Object mapping = Class.forName(mappingClassName).newInstance();
            System.out.println(mapping);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
