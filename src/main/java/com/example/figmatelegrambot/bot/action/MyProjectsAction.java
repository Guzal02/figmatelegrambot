package com.example.figmatelegrambot.bot.action;

import com.example.figmatelegrambot.model.figma.entity.FigmaTeam;
import com.example.figmatelegrambot.model.figma.to.FigmaProjectTo;
import com.example.figmatelegrambot.model.figma.to.FigmaTeamTo;
import com.example.figmatelegrambot.service.ProjectService;
import com.example.figmatelegrambot.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MyProjectsAction implements Action {

    public static final String MY_PROJECTS = "/my_projects";
    private final TeamService teamService;
    private final ProjectService projectService;


    @Override
    public BotApiMethod handle(Update update) {

        var userId = update.getMessage().getFrom().getId();
        var chatId = update.getMessage().getChatId().toString();

        List<FigmaTeam> figmaTeamsByUserId = teamService.getFigmaTeamsByUserId(userId);

        if (figmaTeamsByUserId == null) {
            return new SendMessage(chatId, "Команды Figma с проектами не найдены, для создания бота необходимо в командах бота выбрать - /bot_for_comments");
        }
        List<FigmaTeamTo> figmaTeamTos = figmaTeamsByUserId
                .stream()
                .map(team -> FigmaTeamTo.builder()
                        .figmaTeamId(team.getFigmaTeamId())
                        .name(team.getName())
                        .build())
                .toList();
        return InlineKeyboard.ListOfProjectsInlineKeyboard(userId, figmaTeamTos, ActionConstants.TEAM_ID_CALLBACK, false);

    }

    @Override
    public BotApiMethod callback(Update update) {
        var userId = update.getCallbackQuery().getFrom().getId();
        var callData = update.getCallbackQuery().getData();

        String teamId = "";
        if (callData.contains(ActionConstants.TEAM_ID_CALLBACK)) {
            teamId = callData.replace(ActionConstants.TEAM_ID_CALLBACK + " - ", "");
        }

        List<FigmaProjectTo> figmaProjectTos = projectService.getByFigmaTeamId(teamId)
                .stream()
                .map(project -> FigmaProjectTo.builder()
                        .id(project.getFigmaProjectId())
                        .name(project.getName())
                        .build())
                .toList();
        return InlineKeyboard.ListOfProjectsInlineKeyboard(userId, figmaProjectTos, ActionConstants.PROJECT_ID_CALLBACK, true);

    }

    @Override
    public String getName() {
        return MY_PROJECTS;
    }
}
