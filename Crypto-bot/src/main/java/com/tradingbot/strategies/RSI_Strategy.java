package com.tradingbot.strategies;

import com.tradingbot.database.DatabaseManager;
import com.tradingbot.model.TradeLog;
import org.example.example.tradingbot.telegram.TelegramBot;

import java.sql.Timestamp;

public class RSI_Strategy implements Runnable {
    private final DatabaseManager dbManager;
    private final TelegramBot telegramBot;

    public RSI_Strategy(DatabaseManager dbManager, TelegramBot telegramBot) {
        this.dbManager = dbManager;
        this.telegramBot = telegramBot;
    }

    @Override
    public void run() {
        double rsiValue = calculateRSI();
        double price = getCurrentPrice();

        if (rsiValue > 70) {
            TradeLog log = new TradeLog(new Timestamp(System.currentTimeMillis()), "RSI Strategy", "SELL", price, 1);
            dbManager.saveTradeLog(log);
            telegramBot.sendTradeLog(log);
        } else if (rsiValue < 30) {
            TradeLog log = new TradeLog(new Timestamp(System.currentTimeMillis()), "RSI Strategy", "BUY", price, 1);
            dbManager.saveTradeLog(log);
            telegramBot.sendTradeLog(log);
        }
    }

    private double calculateRSI() {
        // RSI hesaplama kodu buraya eklenecek
        return Math.random() * 100; // Örnek değer
    }

    private double getCurrentPrice() {
        // Gerçek zamanlı fiyat verisini burada alın
        return 50000; // Örnek fiyat
    }
}
