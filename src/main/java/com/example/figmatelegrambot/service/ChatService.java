package com.example.figmatelegrambot.service;

import com.example.figmatelegrambot.error.FigmaIdNotExistException;
import com.example.figmatelegrambot.model.bot.telegram.entity.Chat;
import com.example.figmatelegrambot.model.bot.telegram.entity.ChatType;
import com.example.figmatelegrambot.model.figma.entity.FigmaProject;
import com.example.figmatelegrambot.repository.ChatRepository;
import com.example.figmatelegrambot.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ProjectRepository projectRepository;

    public Chat saveByFigmaProjectId(String figmaProjectId, Long chatId, ChatType chatType, LocalDateTime time) {

        Chat chatByFigmaProjectId = chatRepository.findChatByFigmaProjectId(figmaProjectId);

        if (chatByFigmaProjectId == null) {
            FigmaProject byFigmaProjectId = projectRepository.findByFigmaProjectId(figmaProjectId);
            if (byFigmaProjectId == null) {
                throw new FigmaIdNotExistException("Не удалось найти проект, figmaProjectId: " + figmaProjectId);
            }
            Chat chat = Chat.builder()
                    .chatId(chatId)
                    .chatTypes(chatType)
                    .createdAt(time)
                    .figmaProject(byFigmaProjectId)
                    .build();

            Chat saved = chatRepository.save(chat);

            log.info("ChatId {}, FigmaProjectId {}, успешно сохранен в базу", saved.getChatId(), saved.getFigmaProject().getFigmaProjectId());
            return saved;
        } else {
            log.info("ChatId {}, FigmaProjectId {}, уже сохранен в базе ", chatByFigmaProjectId.getChatId(), chatByFigmaProjectId.getFigmaProject().getFigmaProjectId());
            return chatByFigmaProjectId;
        }
    }

}
