package com.example.figmatelegrambot.bot.action;

import com.example.figmatelegrambot.model.bot.telegram.entity.User;
import com.example.figmatelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartAction implements Action {

    public static final String START = "/start";

    private static final List<String> ACTIONS = List.of(
            "/start - Команды бота",
            "/bot_for_comments - Создать бота для комментариев",
            "/my_projects - Команды Figma c проектами"
    );

    private final UserService userService;

    @Override
    public String getName() {
        return START;
    }


    @Override
    public BotApiMethod handle(Update update) {
        var chatUser = update.getMessage().getFrom();
        var chatId = update.getMessage().getChatId().toString();

        var out = new StringBuilder();
        out.append("""
                      Я могу помочь создать бота, который будет отправлять актуальные комментарии к вашему проекту на ваш канал или группу.              
                    
                     Выберите действие:    
                """).append("\n");
        for (String action : ACTIONS) {
            out.append(action).append("\n");
        }

        User user = User.builder()
                .userId(chatUser.getId())
                .userName(chatUser.getUserName())
                .firstName(chatUser.getFirstName())
                .lastName(chatUser.getLastName())
                .build();

        User savedUser = userService.saveUserIfNotExist(user);
        //TODO ------ ?????? - каждый раз выходит !!!!!
        log.info("User: {} c id: {}  был сохранен в базу при вызове команды /start", savedUser.getUserName(), savedUser.getId());
        return new SendMessage(chatId, out.toString());
    }

    @Override
    public BotApiMethod callback(Update update) {
        return handle(update);
    }
}