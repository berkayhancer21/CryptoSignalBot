package com.cryptobot;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // Config sınıfından API anahtarlarını al
        Config config = new Config();
        String apiKey = config.getApiKey();
        String secretKey = config.getSecretKey();

        // Binance API istemcisini oluştur
        BinanceAPIClient apiClient = new BinanceAPIClient(apiKey, secretKey);

        // Telegram botunu başlat
        TelegramConfig telegramBot = new TelegramConfig();

        // İzlenecek pariteler
        List<String> symbols = Arrays.asList("RENDERUSDT", "AVAXUSDT", "XRPUSDT", "ARKMUSDT", "BTCUSDT", "BCHUSDT", "FETUSDT", "IDUSDT", "FLOKIUSDT"); // Örnek pariteler
        String interval = "1h"; // 1 saatlik zaman aralığı
        int limit = 1000;

        // MariaDB bağlantısını başlat
        MariadbConfig dbConfig = new MariadbConfig();

        // Parite stratejilerini almak için PariteYonetimi'ni kullan
        for (String symbol : symbols) {
            try {
                // Parite verisini işleyip strateji sonuçlarını al
                Map<String, Map<String, String>> strategyResults = PariteYonetimi.processSymbol(symbol, apiClient, interval, limit);

                // Strateji sonuçlarını al
                Map<String, String> strategies = strategyResults.get(symbol);

                // Sonuçları formatlı şekilde yazdır
                String result = symbol + "\n"
                        + "EMA9_EMA21_Stratejisi: " + strategies.get("EMA9_EMA21_Stratejisi") + "\n"
                        + "MACD_EMA_Stratejisi: " + strategies.get("MACD_EMA_Stratejisi") + "\n"
                        + "Momentum_Stratejisi: " + strategies.get("Momentum_Stratejisi") + "\n"
                        + "RSI_Stratejisi: " + strategies.get("RSI_Stratejisi") + "\n"
                        + "Stochastic_Stratejisi: " + strategies.get("Stochastic_Stratejisi") + "\n"
                        + "BollingerBands_StochasticRSI_Stratejisi: " + strategies.get("BollingerBands_StochasticRSI_Stratejisi") + "\n"; // Yeni strateji ekledim

                System.out.println(result);

                // Telegram'a gönder
                telegramBot.sendToTelegram(result);

                // Veritabanına ekle
                for (Map.Entry<String, String> entry : strategies.entrySet()) {
                    String strategyName = entry.getKey();
                    String signalType = entry.getValue();
                    dbConfig.insertLog(symbol, strategyName, signalType);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Programı sonlandır
        System.exit(0);
    }
}
