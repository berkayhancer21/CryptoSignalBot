package com.cryptobot.stratejiler;

import java.util.List;

public class Momentum_stratejisi {

    // Momentum hesaplama fonksiyonu
    public static double calculateMomentum(List<Double> closePrices) {
        if (closePrices.size() < 2) {
            throw new IllegalArgumentException("Momentum hesaplamak için yeterli veri yok.");
        }
        double latestPrice = closePrices.get(closePrices.size() - 1); // Son fiyat
        double initialPrice = closePrices.get(0); // İlk fiyat
        return latestPrice - initialPrice;
    }

    // Momentum stratejisi sinyal üretimi
    public static String generateSignal(double momentum) {
        if (momentum > 0) {
            return "AL"; // Momentum pozitifse AL sinyali
        } else if (momentum < 0) {
            return "SAT"; // Momentum negatifse SAT sinyali
        } else {
            return "NONE"; // Momentum sıfırsa NONE sinyali
        }
    }
}
