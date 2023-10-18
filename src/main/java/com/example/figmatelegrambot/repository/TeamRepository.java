package com.example.figmatelegrambot.repository;

import com.example.figmatelegrambot.model.bot.telegram.entity.User;
import com.example.figmatelegrambot.model.figma.entity.FigmaTeam;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends BaseRepository<FigmaTeam> {

    @Query("SELECT t FROM FigmaTeam t WHERE t.figmaTeamId = :figmaTeamId")
    FigmaTeam findFigmaTeamByFigmaTeamId(String figmaTeamId);

}
