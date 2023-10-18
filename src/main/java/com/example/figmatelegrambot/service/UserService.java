package com.example.figmatelegrambot.service;

import com.example.figmatelegrambot.model.bot.telegram.entity.User;
import com.example.figmatelegrambot.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUserIfNotExist(User user) {
        User userByUserId = userRepository.findByUserId(user.getUserId());
        if (userByUserId == null) {
            User savedUser = userRepository.save(user);
            log.info("User: {}, c id: {}, успешно сохранен в базу", savedUser.getUserName(), savedUser.getId());
            return savedUser;
        } else {
            log.info("User: {}, c id: {}, уже сохранен в базе", userByUserId.getUserName(), userByUserId.getId());
            return userByUserId;
        }
    }

    public User getUserByUserId(Long userId) {
        var byUserId = userRepository.findByUserId(userId);

        if (byUserId == null) {
            log.info(" Пользователя с UserId: {}  нет в базе", userId);
        }
        return byUserId;
    }

}
