package com.example.figmatelegrambot.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone_number;
}
