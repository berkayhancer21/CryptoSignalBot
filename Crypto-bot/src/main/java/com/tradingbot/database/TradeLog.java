package com.tradingbot.database;

public class TradeLog {
    private final String symbol;
    private final String action;
    private final double price;

    public TradeLog(String symbol, String action, double price) {
        this.symbol = symbol;
        this.action = action;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAction() {
        return action;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "TradeLog{" +
                "symbol='" + symbol + '\'' +
                ", action='" + action + '\'' +
                ", price=" + price +
                '}';
    }
}
