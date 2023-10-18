package com.example.figmatelegrambot.model.figma.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FigmaProjectTo implements FigmaIdentifiable {
    private String id;
    private String name;

    @Override
    public String getFigmaName() {
        return name;
    }

    @Override
    public String getFigmaId() {
        return id;
    }
}

