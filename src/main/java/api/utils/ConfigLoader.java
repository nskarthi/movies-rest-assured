package api.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {
        properties = new Properties();
        try {
            // Path relative to project root
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

    public String getBaseUrl() {
        return properties.getProperty("baseUrl");
    }
}
