package com.agenda.domain.entity;

import com.agenda.utils.SessionMethod;
import com.agenda.utils.SessionStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionEntity {
  private Long id;
  private String title;
  private LocalDateTime plannedAt;
  private Integer duration;
  private SessionMethod method;
  private SessionStatus status;
  private UserEntity user;
  private List<TaskEntity> tasks;
}
