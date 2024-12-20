package com.cryptobot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class MariadbConfig {

    private Properties properties;

    // application.properties dosyasını oku
    public MariadbConfig() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("application.properties bulunamadı!");
                return;
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDbUrl() {
        return properties.getProperty("db.url");
    }

    private String getDbUser() {
        return properties.getProperty("db.user");
    }

    private String getDbPassword() {
        return properties.getProperty("db.password");
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(getDbUrl(), getDbUser(), getDbPassword());
    }

    public void insertLog(String symbol, String strategyName, String signalType) {
        String query = "INSERT INTO strateji_log_tablosu (symbol, strategy_name, signal_type, date) VALUES (?, ?, ?, DATE_ADD(NOW(), INTERVAL 2 HOUR))";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, symbol);
            statement.setString(2, strategyName);
            statement.setString(3, signalType);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Veritabanına ekleme hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
