package com.cryptobot;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private Properties properties;

    public Config() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return;
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getApiKey() {
        return properties.getProperty("binance.apiKey");
    }

    public String getSecretKey() {
        return properties.getProperty("binance.secretKey");
    }
}
