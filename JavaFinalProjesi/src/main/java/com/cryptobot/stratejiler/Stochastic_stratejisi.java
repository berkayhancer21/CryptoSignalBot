package com.cryptobot.stratejiler;

import java.util.*;

public class Stochastic_stratejisi {

    public static String generateSignal(List<Double> closePrices, List<Double> highPrices, List<Double> lowPrices) {
        int period = 14;
        int size = closePrices.size();

        if (size < period) {
            return "NONE";  // Not enough data for the calculation
        }

        double highestHigh = Collections.max(highPrices.subList(size - period, size));
        double lowestLow = Collections.min(lowPrices.subList(size - period, size));
        double latestClose = closePrices.get(size - 1);

        // Calculate the stochastic value
        double stochasticValue = ((latestClose - lowestLow) / (highestHigh - lowestLow)) * 100;

        // Generate signals based on stochastic value
        if (stochasticValue < 20) {
            return "AL";  // Buy signal (oversold)
        } else if (stochasticValue > 80) {
            return "SAT";  // Sell signal (overbought)
        } else {
            return "NONE";  // No signal
        }
    }
}
