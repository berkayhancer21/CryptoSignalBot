package com.cryptobot.stratejiler;

import java.util.List;

public class RSI_stratejisi {

    // RSI hesaplama fonksiyonu
    public static double calculateRSI(List<Double> prices, int period) {
        double gain = 0;
        double loss = 0;

        for (int i = 1; i < period; i++) {
            double change = prices.get(i) - prices.get(i - 1);
            if (change > 0) {
                gain += change;
            } else {
                loss -= change;
            }
        }

        double avgGain = gain / period;
        double avgLoss = loss / period;

        if (avgLoss == 0) {
            return 100; // Hareketsiz piyasa
        }

        double rs = avgGain / avgLoss;
        return 100 - (100 / (1 + rs));
    }

    // RSI stratejisi sinyal üretme fonksiyonu
    public static String generateSignal(double rsiValue) {
        if (rsiValue > 70) {
            return "SAT"; // Satış Sinyali
        } else if (rsiValue < 30) {
            return "AL"; // Satın Alma Sinyali
        }
        return "NONE"; // Bu şartların sağlanmadığı bölgede (30 < rsiDegeri < 70)
    }
}
