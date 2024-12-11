package com.cryptobot;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.SpotClient;
import java.util.HashMap;
import java.util.Map;

public class BinanceAPIClient {

    private SpotClient client;

    // Binance API istemcisini oluştur
    public BinanceAPIClient(String apiKey, String secretKey) {
        this.client = new SpotClientImpl(apiKey, secretKey);
    }

    // Parite verilerini al (OHLCV)
    public String getMarketData(String symbol, String interval, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("interval", interval);
        params.put("limit", limit);

        return client.createMarket().klines(params);
    }
}