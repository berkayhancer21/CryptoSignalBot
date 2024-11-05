package com.tradingbot;

import com.tradingbot.database.DatabaseManager;
import com.tradingbot.model.TradeLog;
import com.tradingbot.telegram.TelegramBot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TradingBotTest {
    private DatabaseManager dbManager;
    private TelegramBot telegramBot;

    @BeforeEach
    public void setUp() {
        dbManager = new DatabaseManager();
        telegramBot = new TelegramBot();
    }

    @AfterEach
    public void tearDown() {
        // Test sonrası gerekli temizlik işlemleri (örneğin, veritabanı kayıtlarını silmek)
    }

    @Test
    public void testSaveTradeLog() {
        TradeLog log = new TradeLog(new Timestamp(System.currentTimeMillis()), "Test Strategy", "BUY", 50000, 1);
        dbManager.saveTradeLog(log);

        // Veritabanından kaydın başarıyla yapıldığını doğrulayın
        // (Bu kısım veritabanı sorgusu ile kontrol edilmeli. Aşağıda bir örnek verilmiştir.)
        // assertEquals(expectedValue, actualValue); // Beklenen değer ile gerçek değer karşılaştırılacak
    }

    @Test
    public void testTelegramBotSendMessage() {
        TradeLog log = new TradeLog(new Timestamp(System.currentTimeMillis()), "Test Strategy", "BUY", 50000, 1);

        // Telegram mesajını gönderme işlevini test et
        telegramBot.sendTradeLog(log);

        // Mesajın gönderilip gönderilmediğini kontrol etmek için uygun bir doğrulama eklenmelidir.
    }

    // Diğer stratejileri test etmek için ek test metotları eklenebilir.
}
