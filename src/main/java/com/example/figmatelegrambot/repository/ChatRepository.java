package com.example.figmatelegrambot.repository;

import com.example.figmatelegrambot.model.bot.telegram.entity.Chat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ChatRepository extends BaseRepository<Chat> {

    @Query("SELECT c FROM Chat c WHERE c.figmaProject.figmaProjectId = :figmaProjectId")
    Chat findChatByFigmaProjectId(String figmaProjectId);
}

