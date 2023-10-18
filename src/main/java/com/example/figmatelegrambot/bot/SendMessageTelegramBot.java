package com.example.figmatelegrambot.bot;

import com.example.figmatelegrambot.bot.action.ChatAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class SendMessageTelegramBot extends TelegramLongPollingBot {
    @Autowired
    public ChatAction chatAction;

    public SendMessageTelegramBot(@Value("${bot.send.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasChannelPost()) {
            var msg = chatAction.callback(update);
            send(msg);
        }
    }


    private void send(BotApiMethod msg) {
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }

    @Override
    public String getBotUsername() {
        return "guzal_ma_bot";
    }


}