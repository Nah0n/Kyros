package com.agenda.domain.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity {
  private Long id;
  private String login;
  // TODO: Do not put the password in the response class
  private String password;
  private LocalDateTime createdAt;
}
