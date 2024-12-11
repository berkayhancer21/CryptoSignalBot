package com.cryptobot;

import com.cryptobot.stratejiler.BollingerBands_StochasticRSI_stratejisi;
import com.cryptobot.stratejiler.EMA9_EMA21_stratejisi;
import com.cryptobot.stratejiler.MACD_EMA_stratejisi;
import com.cryptobot.stratejiler.Momentum_stratejisi;
import com.cryptobot.stratejiler.RSI_stratejisi;
import com.cryptobot.stratejiler.Stochastic_stratejisi;

import java.util.*;
import java.util.concurrent.*;

public class StratejiYonetimi {

    public static Map<String, String> calistirStratejiler(List<Double> closePrices, List<Double> highPrices, List<Double> lowPrices) throws InterruptedException, ExecutionException {

        Map<String, String> strategies = new LinkedHashMap<>();

        // Bir thread havuzu oluşturuluyor. Havuz boyutu, strateji sayısına sayısına eşit olacak şekilde ayarlanır
        ExecutorService strategyExecutor = Executors.newFixedThreadPool(6);
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
            double stochasticDegeri = Stochastic_stratejisi.calculateStochastic(closePrices, highPrices, lowPrices, 14);
            return Stochastic_stratejisi.generateSignal(stochasticDegeri);
        }));

        // BollingerBands_StochasticRSI Stratejisi
        strategyFutures.add(strategyExecutor.submit(() -> {
            return BollingerBands_StochasticRSI_stratejisi.generateSignal(closePrices, 20, 2.0, 14);
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
