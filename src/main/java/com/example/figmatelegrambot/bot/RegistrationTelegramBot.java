package com.example.figmatelegrambot.bot;

import com.example.figmatelegrambot.bot.action.Action;
import com.example.figmatelegrambot.bot.action.ActionConstants;
import com.example.figmatelegrambot.bot.action.BotForCommentsAction;
import com.example.figmatelegrambot.bot.action.HandleCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RegistrationTelegramBot extends TelegramLongPollingBot {

    private final Map<String, String> bindingBy = new ConcurrentHashMap<>();
    private final Map<String, Action> actionsByName;
    private final HandleCallback handleCallback;
    private String teamId = "";
    @Autowired
    private BotForCommentsAction botForCommentsAction;

    public RegistrationTelegramBot(@Value("${bot.reg.token:6488528581:AAFtJ7u6Dw6HsI_ZpSBRo_-b9FyoIzj7beo}") String botToken, List<Action> actions, HandleCallback handleCallback) {
        super(botToken);
        actionsByName = actions.stream()
                .collect(Collectors.toMap(Action::getName, Function.identity()));
        this.handleCallback = handleCallback;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            var key = update.getMessage().getText();
            var chatId = update.getMessage().getChatId().toString();
            if (actionsByName.containsKey(key)) {
                var msg = actionsByName.get(key).handle(update);
                bindingBy.put(chatId, key);
                send(msg);
            } else if (bindingBy.containsKey(chatId)) {
                var msg = actionsByName.get(bindingBy.get(chatId)).callback(update);
                bindingBy.remove(chatId);
                send(msg);
            } else {
                send(new SendMessage(chatId, "Не удалось распознать команду!"));
            }
        } else if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().contains(ActionConstants.PROJECT_ID_CALLBACK)) {
                var msg = handleCallback.callback(update);
                send(msg);
            }
            if (update.getCallbackQuery().getData().contains(ActionConstants.TEAM_ID_CALLBACK)) {
                var chatId = update.getCallbackQuery().getMessage().getChatId().toString();

                teamId = update.getCallbackQuery().getData().replace(ActionConstants.TEAM_ID_CALLBACK + " - ", "");

                var msg = actionsByName.get(bindingBy.get(chatId)).callback(update);
                bindingBy.remove(chatId);
                send(msg);
            }
            if (update.getCallbackQuery().getData().contains(ActionConstants.UPDATE_PROJECT)) {
                var chatId = update.getCallbackQuery().getMessage().getChatId().toString();
                var userId = update.getCallbackQuery().getFrom().getId();
                var msg = botForCommentsAction.getAndSaveFigmaProjects(userId, chatId, teamId);
                teamId = "";
                send(msg);
            }
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
        return "SpringDemoByGuzBot";
    }

}
