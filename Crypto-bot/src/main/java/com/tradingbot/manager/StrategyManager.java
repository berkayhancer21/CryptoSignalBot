package com.tradingbot.manager;

import java.util.ArrayList;
import java.util.List;

public class StrategyManager {
    private final List<Thread> threads = new ArrayList<>();

    public void addStrategy(Runnable strategy) {
        threads.add(new Thread(strategy));
    }

    public void startAll() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void stopAll() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}
