package com.example.figmatelegrambot.service;

import com.example.figmatelegrambot.repository.ProjectRepository;
import com.example.figmatelegrambot.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private TeamRepository teamRepository;
    @InjectMocks
    private ProjectService projectServiceUnderTest;

    @Test
    void getByFigmaTeamId() {
    }

    @Test
    void saveProjects() {
    }
}