package api.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;
    private final String env;

    private ConfigLoader() {
        // 1. Determine environment from command line (-Denv=sit), default to 'dev'
        this.env = System.getProperty("env", "dev").toLowerCase();
        
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
        } catch (Exception e) {
            throw new RuntimeException("Could not load config.properties file.");
        }
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    // Helper to fetch values based on the active environment prefix
    private String getEnvProperty(String key) {
        String fullKey = env + "." + key;
        String value = properties.getProperty(fullKey);
        if (value == null) {
            throw new RuntimeException("Property '" + fullKey + "' not found in config.properties");
        }
        return value;
    }

    public String getBaseUrl() {
        return getEnvProperty("baseUrl");
    }
    
    public String getUserName() {
        return getEnvProperty("username");
    }
    
    public String getPassword() {
        return getEnvProperty("password");
    }
}
