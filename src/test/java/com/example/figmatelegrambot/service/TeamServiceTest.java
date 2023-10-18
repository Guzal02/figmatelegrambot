package com.example.figmatelegrambot.service;

import com.example.figmatelegrambot.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private TeamRepository teamRepository;
    @InjectMocks
    private TeamService teamServiceUnderTest;

    @Test
    void saveFigmaTeamIdAndUser() {
    }

    @Test
    void getFigmaTeamsByUserId() {
    }
}