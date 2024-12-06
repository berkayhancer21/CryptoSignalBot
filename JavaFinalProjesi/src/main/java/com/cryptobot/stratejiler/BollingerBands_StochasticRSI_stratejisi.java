package com.cryptobot.stratejiler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BollingerBands_StochasticRSI_stratejisi {

    // Bollinger Bands hesaplama fonksiyonu
    public static double[] calculateBollingerBands(List<Double> fiyatlar, int period, double stdDevMultiplier) {
        double sma = 0;
        for (int i = fiyatlar.size() - period; i < fiyatlar.size(); i++) {
            sma += fiyatlar.get(i);
        }
        sma /= period;

        double variance = 0;
        for (int i = fiyatlar.size() - period; i < fiyatlar.size(); i++) {
            variance += Math.pow(fiyatlar.get(i) - sma, 2);
        }
        variance /= period;
        double stdDev = Math.sqrt(variance);

        double upperBand = sma + (stdDevMultiplier * stdDev);
        double lowerBand = sma - (stdDevMultiplier * stdDev);

        return new double[]{upperBand, sma, lowerBand};
    }

    // Stochastic RSI hesaplama fonksiyonu
    public static double calculateStochasticRSI(List<Double> fiyatlar, int period) {
        List<Double> rsiValues = new ArrayList<>();
        for (int i = 0; i <= fiyatlar.size() - period; i++) {
            List<Double> sublist = fiyatlar.subList(i, i + period);
            double rsi = RSI_stratejisi.calculateRSI(sublist, period);
            rsiValues.add(rsi);
        }

        double minRSI = Collections.min(rsiValues);
        double maxRSI = Collections.max(rsiValues);

        return (rsiValues.get(rsiValues.size() - 1) - minRSI) / (maxRSI - minRSI);
    }

    // Bollinger Bands - Stochastic RSI stratejisi sinyal üretimi fonksiyonu
    public static String generateSignal(List<Double> fiyatlar, int bbPeriod, double bbStdDevMultiplier, int stochRSIPeriod) {
        double[] bollingerBands = calculateBollingerBands(fiyatlar, bbPeriod, bbStdDevMultiplier);
        double stochRSI = calculateStochasticRSI(fiyatlar, stochRSIPeriod);

        double lowerBand = bollingerBands[2];
        double upperBand = bollingerBands[0];

        if (stochRSI < 0.2 && fiyatlar.get(fiyatlar.size() - 1) < lowerBand) {
            return "AL"; // Satın Alma Sinyali
        } else if (stochRSI > 0.8 && fiyatlar.get(fiyatlar.size() - 1) > upperBand) {
            return "SAT"; // Satış Sinyali
        }
        return "NONE"; // Bu şartların sağlanmadığı bölgede (0.2 < stochRSI < 0.8)
    }
}
