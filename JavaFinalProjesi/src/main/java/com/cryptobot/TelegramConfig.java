package com.cryptobot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import java.util.Properties;
import java.io.InputStream;

public class TelegramConfig extends TelegramLongPollingBot {

    private Properties properties;

    // application.properties dosyasını oku
    public TelegramConfig() {
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

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getPropBotToken() {
        return properties.getProperty("telegram.botToken");
    }

    private String getPropChatId() {
        return properties.getProperty("telegram.chatId");
    }

    public void sendToTelegram(String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(getPropChatId());
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return getPropBotToken();
    }

    @Override
    public String getBotUsername() {
        return "CryptoSignalBot";
    }

    @Override
    public void onUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update update) {
        // Gelen mesajları işlemek için bir metot
    }
}
