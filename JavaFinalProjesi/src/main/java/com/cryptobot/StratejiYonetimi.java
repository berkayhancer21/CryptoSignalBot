package com.cryptobot;

import com.cryptobot.stratejiler.BollingerBands_StochasticRSI_stratejisi; // Add import for BollingerBands_StochasticRSI_stratejisi
import com.cryptobot.stratejiler.EMA9_EMA21_stratejisi;
import com.cryptobot.stratejiler.MACD_EMA_stratejisi;
import com.cryptobot.stratejiler.Momentum_stratejisi;
import com.cryptobot.stratejiler.RSI_stratejisi;
import com.cryptobot.stratejiler.Stochastic_stratejisi;

import java.util.*;
import java.util.concurrent.*;

public class StratejiYonetimi {

    // Kapanış fiyatları, yüksek fiyatlar ve düşük fiyatlar üzerinden stratejileri çalıştırır
    public static Map<String, String> calistirStratejiler(List<Double> closePrices, List<Double> highPrices, List<Double> lowPrices) throws InterruptedException, ExecutionException {
        Map<String, String> strategies = new HashMap<>();

        // Stratejiler için thread havuzu oluştur
        ExecutorService strategyExecutor = Executors.newFixedThreadPool(7); // 7 strateji için thread havuzu (RSI_MACD eklenmiş)
        List<Future<String>> strategyFutures = new ArrayList<>();

        // EMA9_EMA21 Stratejisi
        strategyFutures.add(strategyExecutor.submit(() -> {
            List<Double> kisaEMA = EMA9_EMA21_stratejisi.calculateEMA(closePrices, 9);
            List<Double> uzunEMA = EMA9_EMA21_stratejisi.calculateEMA(closePrices, 21);
            return EMA9_EMA21_stratejisi.generateSignal(kisaEMA, uzunEMA);
        }));

        // MACD_EMA Stratejisi
        strategyFutures.add(strategyExecutor.submit(() -> {
            Map<String, List<Double>> macdData = MACD_EMA_stratejisi.calculateMACD(closePrices, 12, 26, 9);
            return MACD_EMA_stratejisi.generateSignal(macdData);
        }));

        // Momentum Stratejisi
        strategyFutures.add(strategyExecutor.submit(() -> {
            double momentum = Momentum_stratejisi.calculateMomentum(closePrices);
            return Momentum_stratejisi.generateSignal(momentum);
        }));

        // RSI Stratejisi
        strategyFutures.add(strategyExecutor.submit(() -> {
            double rsiDegeri = RSI_stratejisi.calculateRSI(closePrices, 14);
            return RSI_stratejisi.generateSignal(rsiDegeri);
        }));

        // Stochastic Stratejisi
        strategyFutures.add(strategyExecutor.submit(() -> {
            String stochasticSignal = Stochastic_stratejisi.generateSignal(closePrices, highPrices, lowPrices);
            return stochasticSignal;
        }));

        // BollingerBands_StochasticRSI Stratejisi
        strategyFutures.add(strategyExecutor.submit(() -> {
            String bollingerStochRSISignal = BollingerBands_StochasticRSI_stratejisi.generateSignal(closePrices, 20, 2.0, 14); // Parametreleri kendinize göre ayarlayın
            return bollingerStochRSISignal;
        }));


        // Sonuçları birleştir
        strategies.put("EMA9_EMA21_Stratejisi", strategyFutures.get(0).get());
        strategies.put("MACD_EMA_Stratejisi", strategyFutures.get(1).get());
        strategies.put("Momentum_Stratejisi", strategyFutures.get(2).get());
        strategies.put("RSI_Stratejisi", strategyFutures.get(3).get());
        strategies.put("Stochastic_Stratejisi", strategyFutures.get(4).get());
        strategies.put("BollingerBands_StochasticRSI_Stratejisi", strategyFutures.get(5).get());


        strategyExecutor.shutdown();
        return strategies;
    }
}
