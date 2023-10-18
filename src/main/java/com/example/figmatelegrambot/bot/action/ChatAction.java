package com.example.figmatelegrambot.bot.action;

import com.example.figmatelegrambot.model.bot.telegram.entity.ChatType;
import com.example.figmatelegrambot.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatAction {

    private final ChatService chatService;

    public BotApiMethod callback(Update update) {
        var chatId = update.getChannelPost().getChatId();
        var text = update.getChannelPost().getText();
        LocalDateTime createdAt = LocalDateTime.ofEpochSecond(update.getChannelPost().getDate(), 0, ZoneOffset.UTC);

        String figmaProjectId = "";

        if (text.contains("/")) {
            figmaProjectId = text.replace("/", "");
        }
// TODO принимать имя проекта - id проекта!
        chatService.saveByFigmaProjectId(figmaProjectId, chatId, ChatType.TELEGRAM, createdAt);

        return new SendMessage(chatId.toString(), "Бот успешно прикреплен к вашему каналу, готов к работе!");
    }
}
