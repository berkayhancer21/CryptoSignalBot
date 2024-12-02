package com.cryptobot;

import java.sql.*;

public class MariadbConfig {
    private static final String URL = "jdbc:mariadb://157.173.104.238:3306/TradeBot";
    private static final String USER = "balabi4359";
    private static final String PASSWORD = "Aliolkac4310*";

    private Connection conn;

    // Constructor: Bağlantıyı başlat
    public MariadbConfig() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Veritabanına başarıyla bağlanıldı!");
        } catch (SQLException e) {
            System.out.println("Veritabanı bağlantı hatası: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Veritabanına kayıt ekleme
    public void insertLog(String symbol, String strategyName, String signalType) {
        String insertQuery = "INSERT INTO bot_log (parite_ismi, strateji_ismi, sinyal_turu, islem_tarihi) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, symbol); // parite_ismi
            pstmt.setString(2, strategyName); // strateji_ismi
            pstmt.setString(3, signalType); // sinyal_turu
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis())); // islem_tarihi

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Veri başarıyla veritabanına eklendi: " + symbol + " - " + strategyName + " - " + signalType);
            }
        } catch (SQLException e) {
            System.out.println("Veritabanı ekleme hatası: " + e.getMessage());
        }
    }

    // Bağlantıyı kapatma (gerekirse)
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Veritabanı bağlantısı kapatıldı.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
