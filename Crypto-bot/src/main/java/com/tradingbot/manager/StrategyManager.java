package com.tradingbot.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StrategyManager {
    private final List<Runnable> strategies = new ArrayList<>();
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

    public void addStrategy(Runnable strategy) {
        strategies.add(strategy);
    }

    public void startAll() {
        for (Runnable strategy : strategies) {
            executorService.scheduleAtFixedRate(strategy, 0, 1, TimeUnit.MINUTES);
        }
    }

    public void stopAll() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
