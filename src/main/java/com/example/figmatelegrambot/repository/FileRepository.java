package com.example.figmatelegrambot.repository;

import com.example.figmatelegrambot.model.figma.entity.FigmaFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface FileRepository extends BaseRepository<FigmaFile> {

    @Query("SELECT f FROM FigmaFile f WHERE f.figmaProject.figmaProjectId = :figmaProjectId")
    List<FigmaFile> findFigmaFileByFigmaProjectFigmaProjectId(String figmaProjectId);

    @Query("SELECT f FROM FigmaFile f WHERE f.key = :key")
    FigmaFile findFigmaFileByKey(String key);
}
