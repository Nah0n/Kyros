package com.agenda.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserEntity {
    private Long id;
    private String login;
    // TODO: Do not put the password in the response class
    private String password;
    private LocalDateTime createdAt;
}
