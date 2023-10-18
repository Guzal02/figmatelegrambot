package com.example.figmatelegrambot.service;

import com.example.figmatelegrambot.error.FigmaIdNotExistException;
import com.example.figmatelegrambot.model.figma.entity.FigmaFile;
import com.example.figmatelegrambot.model.figma.entity.FigmaProject;
import com.example.figmatelegrambot.model.figma.to.FigmaFilesTo;
import com.example.figmatelegrambot.repository.FileRepository;
import com.example.figmatelegrambot.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileService fileServiceUnderTest;

    @Test
    void shouldThrowException_whenNotFoundFigmaProject() {
        String figmaProjectId = "12312";
        FigmaFilesTo figmaFilesTo = new FigmaFilesTo();

        when(projectRepository.findByFigmaProjectId(figmaProjectId)).thenReturn(null);

        assertThatThrownBy(() -> fileServiceUnderTest.saveFiles(figmaProjectId, figmaFilesTo))
                .isInstanceOf(FigmaIdNotExistException.class)
                .hasMessageContaining(figmaProjectId);

        verify(projectRepository, times(1)).findByFigmaProjectId(figmaProjectId);
        verify(fileRepository, never()).save(any());
    }

    @Test
    void saveFiles() {
        String figmaProjectId = "12312";
        FigmaFilesTo figmaFilesTo = new FigmaFilesTo();
        FigmaProject figmaProject = new FigmaProject();

        when(projectRepository.findByFigmaProjectId(figmaProjectId)).thenReturn(figmaProject);

        List<FigmaFile> figmaFiles = fileServiceUnderTest.saveFiles(figmaProjectId, figmaFilesTo);


    }
}