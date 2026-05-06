package com.agenda.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class TaskModel {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  @NotNull @NotBlank private String title;

  private Boolean done;

  @ManyToOne
  @JoinColumn(name = "session_id")
  private SessionModel session;

  // TODO: add a Link to upcoming work session here (For space learning for instance)
}
