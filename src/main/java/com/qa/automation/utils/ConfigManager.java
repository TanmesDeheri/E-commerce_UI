package com.qa.automation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config/config.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fileInputStream);
            logger.info("Configuration loaded successfully from {}", CONFIG_FILE_PATH);
        } catch (IOException e) {
            logger.error("Failed to load configuration file: {}", e.getMessage());
            throw new RuntimeException("Configuration file not found at " + CONFIG_FILE_PATH, e);
        }
    }

    public static String getProperty(String key) {
        // 1. Check System Properties (passed via -Dkey=value)
        String value = System.getProperty(key);
        
        // 2. Check Environment Variables
        if (value == null || value.trim().isEmpty()) {
            value = System.getenv(key);
        }
        
        // 3. Fallback to config.properties
        if (value == null || value.trim().isEmpty()) {
            value = properties.getProperty(key);
        }
        
        if (value == null) {
            logger.warn("Property {} not found in System properties, Environment variables, or configuration file", key);
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return (value != null && !value.trim().isEmpty()) ? value : defaultValue;
    }
}
