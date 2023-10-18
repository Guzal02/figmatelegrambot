package com.example.figmatelegrambot.model.figma.to;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FigmaFilesTo {
    String name;
    List<FigmaFileTo> files;
}
