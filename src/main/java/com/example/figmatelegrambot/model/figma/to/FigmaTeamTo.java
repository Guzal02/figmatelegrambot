package com.example.figmatelegrambot.model.figma.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FigmaTeamTo implements FigmaIdentifiable {
    private String name;
    @JsonIgnore
    private String figmaTeamId;

    private List<FigmaProjectTo> projects;

    @Override
    public String getFigmaName() {
        return name;
    }

    @Override
    public String getFigmaId() {
        return figmaTeamId;
    }
}
