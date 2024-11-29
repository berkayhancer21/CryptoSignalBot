package com.tradingbot.main;

import com.tradingbot.strategies.RSI_Strategy;

public class TradingBotMain {
    public static void main(String[] args) {
        // İşlem yapmak istediğiniz pariteler
        String[] pairs = {"BTCUSDT", "ETHUSDT"};

        for (String pair : pairs) {
            // Her bir parite için ayrı bir thread başlatılır
            Thread strategyThread = new Thread(new RSI_Strategy(pair));
            strategyThread.start();
        }
    }
}
