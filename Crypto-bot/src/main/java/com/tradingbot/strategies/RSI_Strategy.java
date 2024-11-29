package com.tradingbot.strategies;

import com.tradingbot.utils.BinanceAPIHelper;
import org.json.JSONArray;

public class RSI_Strategy implements Runnable {

    private final String symbol;

    public RSI_Strategy(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Binance API'den veri çek
                String response = BinanceAPIHelper.getKlines(symbol, "15m", 14);
                if (response != null) {
                    JSONArray klines = new JSONArray(response);

                    // RSI hesapla
                    double rsi = calculateRSI(klines);

                    // Al/Sat sinyalini belirle
                    String action = rsi > 70 ? "SAT" : rsi < 30 ? "AL" : "BEKLE";

                    // Sinyali ekrana yazdır
                    System.out.println("Parite: " + symbol + ", RSI: " + rsi + ", Sinyal: " + action);
                }

                // 15 dakika bekle
                Thread.sleep(15 * 60 * 1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Strateji durduruldu: " + symbol);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double calculateRSI(JSONArray klines) {
        // Örnek RSI hesaplama
        // Gerçek RSI hesaplama için geçmiş fiyatlar üzerinden kazanç/kayıp analizi yapabilirsiniz
        return Math.random() * 100; // Rastgele bir değer (sadece test amaçlı)
    }
}
