package com.cryptobot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class TelegramConfig extends TelegramLongPollingBot {

    private final String botToken = "8090075523:AAFQjIbrpD2icdsudSYaLWAAj2obFJ__XRI";
    private final String chatId = "-4692179703";

    // Telegram botunu oluştur
    public TelegramConfig() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Telegram'a mesaj gönder
    public void sendToTelegram(String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Telegram API'ye bağlanması için kullanılan token bilgisini döndürür
    @Override
    public String getBotToken() {
        return botToken;
    }

    // Telegram bot kullanıcı adını yazmaya yarar
    @Override
    public String getBotUsername() {
        return "YourBotUsername";
    }

    // Telegram botunun güncellemelerini alır
    @Override
    public void onUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update update) {
        // Gelen mesajları işlemek için kullanılan bir metottur
    }

}
