package com.cryptobot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Config sınıfından API anahtarlarını al
        Config config = new Config();
        String apiKey = config.getApiKey();
        String secretKey = config.getSecretKey();

        // Binance API istemcisini oluştur
        BinanceAPIClient apiClient = new BinanceAPIClient(apiKey, secretKey);

        // İzlenecek pariteler
        List<String> symbols = Arrays.asList("BTCUSDT", "AVAXUSDT");
        String interval = "15m"; // 15 dakikalık veriler
        int limit = 1000;

        // Thread havuzu oluştur
        ExecutorService executor = Executors.newFixedThreadPool(symbols.size());

        List<Future<Map<String, Map<String, String>>>> futures = new ArrayList<>();

        // Her bir parite için işlemleri paralel olarak başlat
        for (String symbol : symbols) {
            futures.add(executor.submit(() -> processSymbol(symbol, apiClient, interval, limit)));
        }

        // Sonuçları al ve yazdır
        for (Future<Map<String, Map<String, String>>> future : futures) {
            try {
                Map<String, Map<String, String>> result = future.get();
                result.forEach((symbolResult, strategies) -> {
                    System.out.println(symbolResult);
                    System.out.println("------------");
                    strategies.forEach((strategy, signal) ->
                            System.out.println(strategy + " : " + signal));
                    System.out.println();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Thread havuzunu kapat
        executor.shutdown();
    }

    // Belirli bir paritenin işlenmesi
    public static Map<String, Map<String, String>> processSymbol(String symbol, BinanceAPIClient apiClient, String interval, int limit) {
        Map<String, Map<String, String>> strategyResults = new LinkedHashMap<>();
        try {
            // Market verisini al
            String marketData = apiClient.getMarketData(symbol, interval, limit);

            // Kapanış fiyatlarını işle
            List<Double> closePrices = processMarketData(marketData);
            if (closePrices.isEmpty()) {
                System.out.println(symbol + " için kapanış fiyatları boş.");
                Map<String, String> emptyStrategies = new HashMap<>();
                emptyStrategies.put("EMA Stratejisi", "NONE");
                emptyStrategies.put("RSI Stratejisi", "NONE");
                strategyResults.put(symbol, emptyStrategies);
                return strategyResults;
            }

            // Stratejiler için thread havuzu oluştur
            ExecutorService strategyExecutor = Executors.newFixedThreadPool(2);
            List<Future<String>> strategyFutures = new ArrayList<>();

            // EMA Stratejisi
            strategyFutures.add(strategyExecutor.submit(() -> {
                List<Double> kisaEMA = EMA9_EMA21_stratejisi.calculateEMA(closePrices, 9);
                List<Double> uzunEMA = EMA9_EMA21_stratejisi.calculateEMA(closePrices, 21);
                return EMA9_EMA21_stratejisi.generateSignal(kisaEMA, uzunEMA);
            }));

            // RSI Stratejisi
            strategyFutures.add(strategyExecutor.submit(() -> {
                double rsiDegeri = RSI_stratejisi.calculateRSI(closePrices, 14);
                return RSI_stratejisi.generateSignal(rsiDegeri);
            }));

            // Sonuçları birleştir
            Map<String, String> strategies = new HashMap<>();
            strategies.put("EMA Stratejisi", strategyFutures.get(0).get());
            strategies.put("RSI Stratejisi", strategyFutures.get(1).get());

            strategyResults.put(symbol, strategies);

            strategyExecutor.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return strategyResults;
    }

    // Market verilerini işlemek ve kapanış fiyatlarını almak
    public static List<Double> processMarketData(String marketData) {
        List<Double> closePrices = new ArrayList<>();
        try {
            JSONArray candles = new JSONArray(marketData);

            for (int i = 0; i < candles.length(); i++) {
                JSONArray candle = candles.getJSONArray(i);
                double kapanisFiyati = candle.getDouble(4); // Kapanış fiyatı 4. indeks
                closePrices.add(kapanisFiyati);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return closePrices;
    }
}