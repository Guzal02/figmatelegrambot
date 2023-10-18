package com.example.figmatelegrambot.bot.action;

import com.example.figmatelegrambot.bot.SendMessageTelegramBot;
import com.example.figmatelegrambot.model.figma.entity.FigmaProject;
import com.example.figmatelegrambot.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class HandleCallback {

    private final ProjectService projectService;
    private final SendMessageTelegramBot sendMessageTelegramBot;


    public BotApiMethod handle(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        var chatId = callbackQuery.getMessage().getChatId().toString();
        var callData = callbackQuery.getData();
        String projectId = "";
        if (callData.contains(ActionConstants.PROJECT_ID_CALLBACK)) {
            projectId = callData.replace(ActionConstants.PROJECT_ID_CALLBACK + " - ", "");
        }
        // TODO Если придет пустой calldata надо выбросить Ex!!!!!!!!!
        return getAndSaveProjectFiles(chatId, projectId);
    }

    private BotApiMethod getAndSaveProjectFiles(String chatId, String projectId) {

        var savedFigmaFiles = projectService.upsertFilesInProject(projectId);

        return new SendMessage(chatId, getText(projectId, savedFigmaFiles));
    }

    @NotNull
    private String getText(String projectId, FigmaProject savedFigmaFiles) {
        return "Поздравляю! бот готов " +
                sendMessageTelegramBot.getBotUsername() +
                " для проекта - " +
                savedFigmaFiles.getName() +
                ", добавьте его к своему каналу и отправьте сообщение /" +
                projectId +
                " для активации бота!";
    }

    public BotApiMethod callback(Update update) {
        return handle(update);
    }
}
