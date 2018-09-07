package com.futengwl.pay;


import java.io.IOException;
import java.util.Properties;

public class ConfigPropertiesUtils {
    private static final String PROPERTIES_FILE_NAME = "WXPay.properties";
    private static Properties PROPERTIES = null;

    static {
        try {
            PROPERTIES = PropertiesUtils.loadProperties(PROPERTIES_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ConfigPropertiesUtils() {
    }

    public static Properties getProperties() {
        return PROPERTIES;
    }
    public static String getPropertie(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }

    public static String getPropertie(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static void main(String[] args) {
        String smsUid = ConfigPropertiesUtils.getPropertie("driverClassName");
        System.out.println(smsUid);
    }
}
