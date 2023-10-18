package com.example.figmatelegrambot.repository;

import com.example.figmatelegrambot.model.figma.entity.FigmaProject;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface ProjectRepository extends BaseRepository<FigmaProject> {

     FigmaProject findByFigmaProjectId(String figmaProjectId);

    Optional<FigmaProject> findFigmaProjectByFigmaTeamFigmaTeamId(String figmaTeamId);
}
