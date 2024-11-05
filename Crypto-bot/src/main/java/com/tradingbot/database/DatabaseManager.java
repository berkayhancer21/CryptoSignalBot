package com.tradingbot.database;

import com.tradingbot.model.TradeLog;
import com.tradingbot.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                ConfigManager.getProperty("db.url"),
                ConfigManager.getProperty("db.username"),
                ConfigManager.getProperty("db.password")
        );
    }

    public void saveTradeLog(TradeLog log) {
        String query = "INSERT INTO trade_logs (timestamp, strategy_name, action, price, quantity) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setTimestamp(1, log.getTimestamp());
            pstmt.setString(2, log.getStrategyName());
            pstmt.setString(3, log.getAction());
            pstmt.setDouble(4, log.getPrice());
            pstmt.setDouble(5, log.getQuantity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
