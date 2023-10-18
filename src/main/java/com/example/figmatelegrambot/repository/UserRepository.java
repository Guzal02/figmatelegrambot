package com.example.figmatelegrambot.repository;

import com.example.figmatelegrambot.model.bot.telegram.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {

    @Query("SELECT u FROM User u WHERE u.userId = :userId")
    User findByUserId(Long userId);
}
