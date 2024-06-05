package ru.paramonova.mongoProject.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private String username;
    private String password;
}
