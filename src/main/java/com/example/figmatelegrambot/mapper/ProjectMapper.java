package com.example.figmatelegrambot.mapper;

import com.example.figmatelegrambot.model.figma.entity.FigmaProject;
import com.example.figmatelegrambot.model.figma.to.FigmaProjectTo;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    FigmaProjectTo map(FigmaProject project);

    @InheritInverseConfiguration
    FigmaProject map(FigmaProjectTo projectTo);
}
