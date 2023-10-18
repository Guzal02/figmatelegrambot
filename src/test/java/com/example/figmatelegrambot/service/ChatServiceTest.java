package com.example.figmatelegrambot.service;

import com.example.figmatelegrambot.error.FigmaIdNotExistException;
import com.example.figmatelegrambot.model.bot.telegram.entity.Chat;
import com.example.figmatelegrambot.model.bot.telegram.entity.ChatType;
import com.example.figmatelegrambot.model.figma.entity.FigmaProject;
import com.example.figmatelegrambot.repository.ChatRepository;
import com.example.figmatelegrambot.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ChatServiceTest {
    @Mock
    private ChatRepository chatRepository;
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ChatService chatServiceUnderTest;

    @Captor
    private ArgumentCaptor<Chat> chatCaptor;

    @Test
    void shouldSaveAndReturnNewChat_whenSaveByFigmaProjectId() {

        var figmaProjectId = "12312";
        long chatId = 15245L;
        LocalDateTime time = LocalDateTime.now();
        ChatType telegram = ChatType.TELEGRAM;
        FigmaProject figmaProject = new FigmaProject();

        var expected = new Chat();
        expected.setFigmaProject(figmaProject);

        when(chatRepository.findChatByFigmaProjectId(figmaProjectId)).thenReturn(null);
        when(projectRepository.findByFigmaProjectId(figmaProjectId)).thenReturn(figmaProject);
        when(chatRepository.save(Mockito.any(Chat.class))).thenReturn(expected);

        Chat actual = chatServiceUnderTest.saveByFigmaProjectId(figmaProjectId, chatId, telegram, time);

        assertEquals(expected, actual);

        verify(chatRepository, times(1)).findChatByFigmaProjectId(figmaProjectId);
        verify(projectRepository, times(1)).findByFigmaProjectId(figmaProjectId);
        verify(chatRepository, times(1)).save(chatCaptor.capture());

        Chat chatCaptorValue = chatCaptor.getValue();

        assertEquals(chatId, chatCaptorValue.getChatId());
        assertEquals(telegram, chatCaptorValue.getChatTypes());
        assertEquals(time, chatCaptorValue.getCreatedAt());
        assertEquals(figmaProject, chatCaptorValue.getFigmaProject());

    }

    @Test
    void shouldThrowException_whenNotFoundFigmaProject() {

        var figmaProjectId = "12312";
        long chatId = 15245L;
        LocalDateTime time = LocalDateTime.now();
        ChatType telegram = ChatType.TELEGRAM;
        FigmaProject figmaProject = new FigmaProject();

        var expected = new Chat();
        expected.setFigmaProject(figmaProject);

        when(chatRepository.findChatByFigmaProjectId(figmaProjectId)).thenReturn(null);
        when(projectRepository.findByFigmaProjectId(figmaProjectId)).thenReturn(null);

        assertThatThrownBy(() -> chatServiceUnderTest.saveByFigmaProjectId(figmaProjectId, chatId, telegram, time))
                .isInstanceOf(FigmaIdNotExistException.class)
                .hasMessageContaining(figmaProjectId);

        verify(chatRepository, times(1)).findChatByFigmaProjectId(figmaProjectId);
        verify(projectRepository, times(1)).findByFigmaProjectId(figmaProjectId);
        verify(chatRepository, never()).save(any());

    }

    @Test
    void shouldNotSaveButReturnExistedChat_whenFindChatByFigmaProjectId() {

        var figmaProjectId = "12312";
        long chatId = 15245L;
        LocalDateTime time = LocalDateTime.now();
        ChatType telegram = ChatType.TELEGRAM;
        FigmaProject figmaProject = new FigmaProject();

        var expected = new Chat();
        expected.setFigmaProject(figmaProject);

        when(chatRepository.findChatByFigmaProjectId(figmaProjectId)).thenReturn(expected);

        Chat actual = chatServiceUnderTest.saveByFigmaProjectId(figmaProjectId, chatId, telegram, time);

        assertEquals(expected, actual);

        verify(chatRepository, times(1)).findChatByFigmaProjectId(figmaProjectId);
        verify(projectRepository, never()).findByFigmaProjectId(any());
        verify(chatRepository, never()).save(any());
    }

}