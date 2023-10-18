package com.example.figmatelegrambot.service;

import com.example.figmatelegrambot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userServiceUnderTest;



    @Test
    void saveUserIfNotExist() {


    }

    @Test
    void getUserByUserId() {
    }
}