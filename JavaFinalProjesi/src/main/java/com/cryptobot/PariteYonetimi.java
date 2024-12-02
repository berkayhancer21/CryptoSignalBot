package com.cryptobot;

import java.util.*;

public class PariteYonetimi {

    public static Map<String, Map<String, String>> processSymbol(
            String symbol, BinanceAPIClient apiClient, String interval, int limit) {

        Map<String, Map<String, String>> strategyResults = new LinkedHashMap<>();
        try {
            // Market verisini al
            String marketData = apiClient.getMarketData(symbol, interval, limit);

            // Market verisini işlemeye gönder
            List<Double> closePrices = Config.processMarketData(marketData);
            List<Double> highPrices = Config.processHighPrices(marketData);
            List<Double> lowPrices = Config.processLowPrices(marketData);

            if (closePrices.isEmpty()) {
                System.out.println(symbol + " için kapanış fiyatları boş.");
                Map<String, String> emptyStrategies = new HashMap<>();
                emptyStrategies.put("EMA9_EMA21_Stratejisi", "NONE");
                emptyStrategies.put("MACD_EMA_Stratejisi", "NONE");
                emptyStrategies.put("Momentum_Stratejisi", "NONE");
                emptyStrategies.put("RSI_Stratejisi", "NONE");
                emptyStrategies.put("Stochastic_Stratejisi", "NONE");
                emptyStrategies.put("BollingerBands_StochasticRSI_Stratejisi", "NONE"); // Yeni strateji ekledim
                strategyResults.put(symbol, emptyStrategies);
                return strategyResults;
            }

            // Stratejileri çalıştır
            Map<String, String> strategies = StratejiYonetimi.calistirStratejiler(closePrices, highPrices, lowPrices);

            // Strateji sonuçlarını ekle
            strategyResults.put(symbol, strategies);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return strategyResults;
    }
}
