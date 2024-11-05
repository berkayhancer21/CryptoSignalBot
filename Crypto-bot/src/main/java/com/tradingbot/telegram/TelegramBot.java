package com.tradingbot.telegram;

import com.tradingbot.ConfigManager;
import com.tradingbot.model.TradeLog;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBot extends TelegramLongPollingBot {

    public TelegramBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return ConfigManager.getProperty("telegram.bot.token");
    }

    @Override
    public String getBotUsername() {
        return "TradingBot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Bu bot pasif olduğu için burası boş
    }

    public void sendTradeLog(TradeLog log) {
        String message = "Strategy: " + log.getStrategyName() +
                "\nAction: " + log.getAction() +
                "\nPrice: " + log.getPrice() +
                "\nQuantity: " + log.getQuantity() +
                "\nTimestamp: " + log.getTimestamp();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(ConfigManager.getProperty("telegram.chat.id"));
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
