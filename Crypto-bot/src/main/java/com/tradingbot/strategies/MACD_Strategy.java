package com.tradingbot.strategies;

import com.tradingbot.database.DatabaseManager;
import com.tradingbot.model.TradeLog;
import com.tradingbot.telegram.TelegramBot;

import java.sql.Timestamp;

public class MACD_Strategy implements Runnable {
    private final DatabaseManager dbManager;
    private final TelegramBot telegramBot;

    public MACD_Strategy(DatabaseManager dbManager, TelegramBot telegramBot) {
        this.dbManager = dbManager;
        this.telegramBot = telegramBot;
    }

    @Override
    public void run() {
        double macdValue = calculateMACD();
        double price = getCurrentPrice();

        if (macdValue > 0) {
            TradeLog log = new TradeLog(new Timestamp(System.currentTimeMillis()), "MACD Strategy", "BUY", price, 1);
            dbManager.saveTradeLog(log);
            telegramBot.sendTradeLog(log);
        } else if (macdValue < 0) {
            TradeLog log = new TradeLog(new Timestamp(System.currentTimeMillis()), "MACD Strategy", "SELL", price, 1);
            dbManager.saveTradeLog(log);
            telegramBot.sendTradeLog(log);
        }
    }

    private double calculateMACD() {
        // MACD hesaplama kodu buraya eklenecek
        return Math.random() - 0.5; // Örnek değer
    }

    private double getCurrentPrice() {
        // Gerçek zamanlı fiyat verisini burada alın
        return 50000; // Örnek fiyat
    }
}
