package com.example.figmatelegrambot.mapper;


import com.example.figmatelegrambot.dto.UserDto;
import com.example.figmatelegrambot.model.bot.telegram.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto map(User user);

    @InheritInverseConfiguration
    User map(UserDto userDto);
}
