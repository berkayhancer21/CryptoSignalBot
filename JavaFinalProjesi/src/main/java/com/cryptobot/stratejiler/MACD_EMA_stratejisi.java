package com.cryptobot.stratejiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MACD_EMA_stratejisi {

    // EMA hesaplama fonksiyonu
    public static List<Double> calculateEMA(List<Double> prices, int period) {
        List<Double> ema = new ArrayList<>();
        double multiplier = 2.0 / (period + 1);
        double previousEMA = prices.get(0); // İlk EMA'yı başlatmak için başlangıç değeri

        ema.add(previousEMA);

        for (int i = 1; i < prices.size(); i++) {
            double currentEMA = (prices.get(i) - previousEMA) * multiplier + previousEMA;
            ema.add(currentEMA);
            previousEMA = currentEMA;
        }

        return ema;
    }

    // MACD hesaplama fonksiyonu: 12 EMA ve 26 EMA arasındaki farkı hesaplar
    public static Map<String, List<Double>> calculateMACD(List<Double> prices, int shortPeriod, int longPeriod, int signalPeriod) {
        List<Double> shortEMA = calculateEMA(prices, shortPeriod); // 12 periyot EMA
        List<Double> longEMA = calculateEMA(prices, longPeriod); // 26 periyot EMA

        // MACD hattı: kısa EMA ile uzun EMA arasındaki fark
        List<Double> macdLine = new ArrayList<>();
        for (int i = 0; i < shortEMA.size(); i++) {
            macdLine.add(shortEMA.get(i) - longEMA.get(i));
        }

        // Signal hattı: MACD hattının EMA'sı
        List<Double> signalLine = calculateEMA(macdLine, signalPeriod);

        Map<String, List<Double>> macdData = new HashMap<>(); // MACD ve Signal hattı verilerini içeren bir Map
        macdData.put("macdLine", macdLine); // MACD hattı map'e eklendi
        macdData.put("signalLine", signalLine); // Signal hattı map'e eklendi

        return macdData;
    }

    // MACD-EMA stratejisi sinyal üretimi fonksiyonu
    public static String generateSignal(Map<String, List<Double>> macdData) {
        List<Double> macdLine = macdData.get("macdLine");
        List<Double> signalLine = macdData.get("signalLine");

        int lastIndex = macdLine.size() - 1;

        if (macdLine.get(lastIndex) > signalLine.get(lastIndex) && macdLine.get(lastIndex - 1) <= signalLine.get(lastIndex - 1)) {
            return "AL"; // MACD, Signal hattını yukarı kesiyor
        } else if (macdLine.get(lastIndex) < signalLine.get(lastIndex) && macdLine.get(lastIndex - 1) >= signalLine.get(lastIndex - 1)) {
            return "SAT"; // MACD, Signal hattını aşağı kesiyor
        } else {
            return "NONE"; // Diğer durumlar
        }
    }
}
