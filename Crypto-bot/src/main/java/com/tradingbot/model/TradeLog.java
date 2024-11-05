package com.tradingbot.model;

import java.sql.Timestamp;

public class TradeLog {
    private Timestamp timestamp;
    private String strategyName;
    private String action;
    private double price;
    private double quantity;

    public TradeLog(Timestamp timestamp, String strategyName, String action, double price, double quantity) {
        this.timestamp = timestamp;
        this.strategyName = strategyName;
        this.action = action;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter metodları
    public Timestamp getTimestamp() { return timestamp; }
    public String getStrategyName() { return strategyName; }
    public String getAction() { return action; }
    public double getPrice() { return price; }
    public double getQuantity() { return quantity; }
}
