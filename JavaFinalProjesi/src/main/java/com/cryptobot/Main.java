package com.cryptobot;

import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        BinanceConfig binanceConfig = new BinanceConfig();
        String apiKey = binanceConfig.getApiKey();
        String secretKey = binanceConfig.getSecretKey();

        BinanceAPIClient apiClient = new BinanceAPIClient(apiKey, secretKey);

        TelegramConfig telegramBot = new TelegramConfig();

        // İzlenecek pariteler belirlenir
        List<String> symbols = Arrays.asList("RENDERUSDT", "AVAXUSDT", "XRPUSDT", "ARKMUSDT", "BTCUSDT", "BCHUSDT", "FETUSDT", "IDUSDT", "FLOKIUSDT");
        String interval = "1h";
        int limit = 1000;

        MariadbConfig dbConfig = new MariadbConfig();

        // Bir thread havuzu oluşturuluyor. Havuz boyutu, izlenecek parite sayısına eşit olacak şekilde ayarlanır
        ExecutorService executorService = Executors.newFixedThreadPool(symbols.size());

        //  Pariteleri işlemek için bir CompletableFuture listesi oluşturulur
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (String symbol : symbols) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    // Parite verisini işleyip strateji sonuçlarını al
                    Map<String, Map<String, String>> strategyResults = PariteYonetimi.processSymbol(symbol, apiClient, interval, limit);

                    // Strateji sonuçlarını al
                    Map<String, String> strategies = strategyResults.get(symbol);

                    String result = symbol + "\n"
                            + "EMA9_EMA21_Stratejisi: " + strategies.get("EMA9_EMA21_Stratejisi") + "\n"
                            + "MACD_EMA_Stratejisi: " + strategies.get("MACD_EMA_Stratejisi") + "\n"
                            + "Momentum_Stratejisi: " + strategies.get("Momentum_Stratejisi") + "\n"
                            + "RSI_Stratejisi: " + strategies.get("RSI_Stratejisi") + "\n"
                            + "Stochastic_Stratejisi: " + strategies.get("Stochastic_Stratejisi") + "\n"
                            + "BollingerBands_StochasticRSI_Stratejisi: " + strategies.get("BollingerBands_StochasticRSI_Stratejisi") + "\n";

                    System.out.println(result);

                    telegramBot.sendToTelegram(result);

                    for (Map.Entry<String, String> entry : strategies.entrySet()) {
                        String strategyName = entry.getKey();
                        String signalType = entry.getValue();
                        dbConfig.insertLog(symbol, strategyName, signalType);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, executorService);

            futures.add(future);
        }

        // Tüm işlemlerin tamamlanmasını bekle
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // ExecutorService'i kapat
        executorService.shutdown();

        System.out.println("Tüm pariteler işlendi ve veritabanına kaydedildi.");

        System.exit(0);

    }
}
