package com.example.figmatelegrambot.configuration;

import com.example.figmatelegrambot.bot.RegistrationTelegramBot;
import com.example.figmatelegrambot.bot.SendMessageTelegramBot;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class FigmaTelegramBotConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(RegistrationTelegramBot registrationTelegramBot, SendMessageTelegramBot sendMessageTelegramBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(registrationTelegramBot);
        api.registerBot(sendMessageTelegramBot);
        return api;
    }

}
