package com.example.figmatelegrambot.bot.action;

import com.example.figmatelegrambot.model.figma.to.FigmaIdentifiable;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboard {
    public static SendMessage ListOfProjectsInlineKeyboard(long chatId, List<? extends FigmaIdentifiable> projects, String partOfCallbackData, boolean isMyProjects) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Отлично! Выберите из списка   : ");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtons = projects.stream()
                .map(project -> getInlineKeyboardButton(project.getFigmaName(), partOfCallbackData + " - " + project.getFigmaId()))
                .toList();
        rowsInline.add(inlineKeyboardButtons);

        if (isMyProjects) {
            InlineKeyboardButton updateProjectButton = getInlineKeyboardButton("Обновить проекты", ActionConstants.UPDATE_PROJECT);
            List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
            keyboardButtons.add(updateProjectButton);
            rowsInline.add(keyboardButtons);
        }
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return message;
    }

    private static InlineKeyboardButton getInlineKeyboardButton(String buttonText, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonText);
        button.setCallbackData(callbackData);
        return button;
    }
}
