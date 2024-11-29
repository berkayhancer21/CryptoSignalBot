package com.cryptobot;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.SpotClient;
import java.util.HashMap;
import java.util.Map;

public class BinanceAPIClient {

    private SpotClient client;

    public BinanceAPIClient(String apiKey, String secretKey) {
        Map<String, String> config = new HashMap<>();
        config.put("API_KEY", apiKey);
        config.put("SECRET_KEY", secretKey);
        this.client = new SpotClientImpl(apiKey, secretKey);
    }

    // Parite verilerini al (OHLCV)
    public String getMarketData(String symbol, String interval, int limit) {
        String endpoint = "/api/v3/klines";
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("interval", interval);
        params.put("limit", limit);

        return client.createMarket().klines(params);
    }
}