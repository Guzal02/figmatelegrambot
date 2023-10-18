package com.example.figmatelegrambot.bot.action;

import com.example.figmatelegrambot.client.FigmaClient;
import com.example.figmatelegrambot.model.figma.entity.FigmaProject;
import com.example.figmatelegrambot.model.figma.entity.FigmaTeam;
import com.example.figmatelegrambot.service.ProjectService;
import com.example.figmatelegrambot.service.TeamService;
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
public class BotForCommentsAction implements Action {
    public static final String BOT_FOR_COMMENTS = "/bot_for_comments";

    private final FigmaClient figmaClient;
    private final TeamService teamService;
    private final ProjectService projectService;

    @Override
    public BotApiMethod handle(Update update) {
        var msg = update.getMessage();
        var chatId = msg.getChatId().toString();
        var text = "Для создания нового бота необходимо введите team_id из Figma";
        return new SendMessage(chatId, text);
    }

    @Override
    public BotApiMethod callback(Update update) {
        var msg = update.getMessage();
        var teamId = msg.getText();
        var chatId = msg.getChatId().toString();
        var userId = msg.getFrom().getId();

        return getAndSaveFigmaProjects(userId, chatId, teamId);
    }

    @Override
    public String getName() {
        return BOT_FOR_COMMENTS;
    }

    public BotApiMethod getAndSaveFigmaProjects(Long userId, String chatId, String figmaTeamId) {
        SendMessage message;
        var teamTo = figmaClient.getTeamProjects(figmaTeamId);

        if (teamTo.getName() == null) {
            log.info(" Неверный figmaTeamId: {}", figmaTeamId);
            return new SendMessage(chatId, "в Figma по team_id: " + "'" + figmaTeamId + "'" + " команда не найдена. Нужно перейти в команду бота /bot_for_comments и ввести корректный team_id");
        } else {
            FigmaTeam savedFigmaTeamIdAndUserInFigmaTeam = teamService.saveFigmaTeamIdAndUser(teamTo, figmaTeamId, userId);

            List<FigmaProject> savedProjects = projectService.saveProjects(teamTo, figmaTeamId);

            log.info("по figmaTeamId: {} выгружено из Figma проектов в количестве {} шт. и сохранены в базу под teamId: {}", figmaTeamId, savedProjects.size(), savedFigmaTeamIdAndUserInFigmaTeam.getId());

            message = InlineKeyboard.ListOfProjectsInlineKeyboard(userId, teamTo.getProjects(), ActionConstants.PROJECT_ID_CALLBACK, false);
        }

        return message;
    }

}