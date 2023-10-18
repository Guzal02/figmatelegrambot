package com.example.figmatelegrambot.service;

import com.example.figmatelegrambot.client.FigmaClient;
import com.example.figmatelegrambot.error.FigmaIdNotExistException;
import com.example.figmatelegrambot.model.figma.entity.FigmaFile;
import com.example.figmatelegrambot.model.figma.entity.FigmaProject;
import com.example.figmatelegrambot.model.figma.entity.FigmaTeam;
import com.example.figmatelegrambot.model.figma.to.FigmaTeamTo;
import com.example.figmatelegrambot.repository.ProjectRepository;
import com.example.figmatelegrambot.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProjectService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz");

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final FigmaClient figmaClient;


    //    public FigmaProjectTo get(String figmaProjectId) {
//        // TODO если репозиторий вернет null  выбросить EX!!!!
//        FigmaProject byFigmaProjectId = projectRepository.findByFigmaProjectId(figmaProjectId);
//        return projectMapper.map(byFigmaProjectId);
//    }

    public List<FigmaProject> getByFigmaTeamId(String figmaTeamId) {
        return projectRepository.findFigmaProjectByFigmaTeamFigmaTeamId(figmaTeamId)
                .stream()
                .toList();
    }

    @Transactional
    public List<FigmaProject> saveProjects(FigmaTeamTo teamTo, String FigmaTeamId) {

        FigmaTeam figmaTeamByFigmaTeamId = teamRepository.findFigmaTeamByFigmaTeamId(FigmaTeamId);

        return teamTo.getProjects()
                .stream()
                .map(project -> FigmaProject.builder()
                        .figmaProjectId(project.getFigmaId())
                        .name(project.getName())
                        .figmaTeam(figmaTeamByFigmaTeamId)
                        .build())
                .map(this::saveFigmaProjectIfNotExist)
                .toList();
    }

    @NotNull
    private FigmaProject saveFigmaProjectIfNotExist(FigmaProject project) {
        FigmaProject byFigmaProjectId = projectRepository.findByFigmaProjectId(project.getFigmaProjectId());
        if (byFigmaProjectId == null) {
            FigmaProject savedFigmaProject = projectRepository.save(project);
            log.info("ProjectName: {}, figmaTeamId: {}, успешно сохранен", savedFigmaProject.getName(), savedFigmaProject.getFigmaTeam().getId());
            return savedFigmaProject;
        } else {
            log.info("Проект с FigmaProjectId: {}, в базе существует!", byFigmaProjectId.getFigmaProjectId());
            return byFigmaProjectId;
        }
    }

    @Transactional
    public FigmaProject upsertFilesInProject(String figmaProjectId) {

        FigmaProject byFigmaProjectId = projectRepository.findByFigmaProjectId(figmaProjectId);

        if (byFigmaProjectId == null) {
            throw new FigmaIdNotExistException("Не удалось найти проект, figmaProjectId: " + figmaProjectId);
        }

        var projectFiles = figmaClient.getProjectFiles(figmaProjectId);

        List<FigmaFile> figmaFiles = projectFiles.getFiles()
                .stream()
                .map(file -> FigmaFile.builder()
                        .key(file.getKey())
                        .name(file.getName())
                        .lastModified(LocalDateTime.parse(file.getLastModified(), FORMATTER))
                        .thumbnailUrl(file.getThumbnailUrl())
                        .figmaProject(byFigmaProjectId)
                        .build())
                .toList();

        byFigmaProjectId.getFigmaFileList().clear();
        byFigmaProjectId.getFigmaFileList().addAll(figmaFiles);

        return projectRepository.save(byFigmaProjectId);
    }
}
