package com.cryptobot.stratejiler;

import java.util.*;

public class Stochastic_stratejisi {
    // Stochastic hesaplama fonksiyonu
    public static double calculateStochastic(List<Double> closePrices, List<Double> highPrices, List<Double> lowPrices, int period) {
        int size = closePrices.size();

        // Yüksek ve düşük fiyatlar arasındaki en yüksek ve en düşük değerleri bul
        double highestHigh = Collections.max(highPrices.subList(size - period, size));
        double lowestLow = Collections.min(lowPrices.subList(size - period, size));
        double latestClose = closePrices.get(size - 1);

        // Stochastic değerini hesapla
        return ((latestClose - lowestLow) / (highestHigh - lowestLow)) * 100;
    }

    // Stochastic stratejisi sinyal üretimi fonksiyonu
    public static String generateSignal(double stochasticValue) {
        if (stochasticValue < 20) {
            return "AL";  // Satın Alma Sinyali (Aşırı Satış Bölgesinde)
        } else if (stochasticValue > 80) {
            return "SAT";  // Satış Sinyali  (Aşırı Alım Bölgesinde)
        }
        return "NONE";  // Bu şartların sağlanmadığı bölgede (20 < stochasticValue < 80)
    }
}
