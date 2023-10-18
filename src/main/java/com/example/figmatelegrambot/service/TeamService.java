package com.example.figmatelegrambot.service;

import com.example.figmatelegrambot.model.bot.telegram.entity.User;
import com.example.figmatelegrambot.model.figma.entity.FigmaTeam;
import com.example.figmatelegrambot.model.figma.to.FigmaTeamTo;
import com.example.figmatelegrambot.repository.TeamRepository;
import com.example.figmatelegrambot.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TeamService {

    private final UserService userService;
    private final TeamRepository teamRepository;

    @Transactional
    public FigmaTeam saveFigmaTeamIdAndUser(FigmaTeamTo figmaTeamTo, String figmaTeamId, Long userId) {

        FigmaTeam figmaTeamEntity = FigmaTeam.builder()
                .figmaTeamId(figmaTeamId)
                .name(figmaTeamTo.getName())
                .build();

        FigmaTeam savedUserInFigmaTeam = saveUserInFigmaTeam(userId, figmaTeamEntity);
        log.info(" User c UserId: {}, сохранен в Team: {} ", userId, savedUserInFigmaTeam.getName());

        FigmaTeam figmaTeamByFigmaTeamId = teamRepository.findFigmaTeamByFigmaTeamId(figmaTeamId);

        if (figmaTeamByFigmaTeamId == null) {
            FigmaTeam savedFigmaTeam = teamRepository.save(figmaTeamEntity);
            log.info("TeamName: {}, c figmaTeamId: {}, успешно сохранен в базу", savedFigmaTeam.getName(), savedFigmaTeam.getFigmaTeamId());
            return savedFigmaTeam;
        } else {
            log.info("TeamName: {}, c figmaTeamId: {}, уже существует в базе", figmaTeamByFigmaTeamId.getName(), figmaTeamByFigmaTeamId.getFigmaTeamId());
            return figmaTeamByFigmaTeamId;
        }
    }

    private FigmaTeam saveUserInFigmaTeam(Long userId, FigmaTeam figmaTeamEntity) {

        var userByUserId = userService.getUserByUserId(userId);

        if (figmaTeamEntity.getUsers() == null) {
            List<User> userList = new ArrayList<>();
            userList.add(userByUserId);
            figmaTeamEntity.setUsers(userList);
        } else {
            figmaTeamEntity.getUsers().add(userByUserId);
        }
        return figmaTeamEntity;
    }

    public List<FigmaTeam> getFigmaTeamsByUserId(Long userId) {
        // TODO обработать null ex!!!!
        User userByUserId = userService.getUserByUserId(userId);

        if (userByUserId != null) {
            List<FigmaTeam> teams = userByUserId.getTeams();
            if (teams.isEmpty()) {
                log.info("У пользователя UserName: {} c id: {} нет сохраненных Figma команд в базе", userByUserId.getUserName(), userByUserId.getId());
                return null;
            }
            return teams;
        }
        return null;
    }
}
