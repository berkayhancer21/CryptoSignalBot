package com.cryptobot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MariadbConfig {
    // Veritabanı bağlantı bilgileri
    private static final String DB_URL = "jdbc:mysql://157.173.104.238:3306/TradeBot"; // Veritabanı URL
    private static final String DB_USER = "balabi4359"; // Kullanıcı adı
    private static final String DB_PASSWORD = "Aliolkac4310*"; // Şifre

    // Veritabanı bağlantısı kurma metodu
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Log eklemek için kullanılan metot
    public void insertLog(String symbol, String strategyName, String signalType) {
        String query = "INSERT INTO strateji_log_tablosu (symbol, strategy_name, signal_type, date) VALUES (?, ?, ?, DATE_ADD(NOW(), INTERVAL 2 HOUR))";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Parametreleri ayarla
            statement.setString(1, symbol);
            statement.setString(2, strategyName);
            statement.setString(3, signalType);

            // Sorguyu çalıştır
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Veritabanına ekleme hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
