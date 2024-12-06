package com.cryptobot;

import org.json.JSONArray;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BinanceConfig {
    private Properties properties;

    // application.properties dosyasını oku
    public BinanceConfig() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("application.properties bulunamadı!");
                return;
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Market verisini işleyip kapanış fiyatlarını döndüren fonksiyon
    public static List<Double> processClosePrices(String marketData) {
        List<Double> closePrices = new ArrayList<>();

        try {
            // JSON verisini işle
            JSONArray jsonArray = new JSONArray(marketData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray candle = jsonArray.getJSONArray(i);
                double closePrice = candle.getDouble(4); // 4. indeks kapanış fiyatıdır
                closePrices.add(closePrice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return closePrices;
    }

    // Market verisini işleyip yüksek fiyatları döndüren fonksiyon
    public static List<Double> processHighPrices(String marketData) {
        List<Double> highPrices = new ArrayList<>();

        try {
            // JSON verisini işle
            JSONArray jsonArray = new JSONArray(marketData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray candle = jsonArray.getJSONArray(i);
                double highPrice = candle.getDouble(2); // 2. indeks yüksek fiyatıdır
                highPrices.add(highPrice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return highPrices;
    }

    // Market verisini işleyip düşük fiyatları döndüren fonksiyon
    public static List<Double> processLowPrices(String marketData) {
        List<Double> lowPrices = new ArrayList<>();

        try {
            // JSON verisini işle
            JSONArray jsonArray = new JSONArray(marketData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray candle = jsonArray.getJSONArray(i);
                double lowPrice = candle.getDouble(3); // 3. indeks düşük fiyatıdır
                lowPrices.add(lowPrice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lowPrices;
    }

    public String getApiKey() {
        return properties.getProperty("binance.apiKey");
    }

    public String getSecretKey() {
        return properties.getProperty("binance.secretKey");
    }
}
