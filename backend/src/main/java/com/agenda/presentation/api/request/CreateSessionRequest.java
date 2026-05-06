package com.agenda.presentation.api.request;

import com.agenda.utils.SessionMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSessionRequest {
  @NotBlank private String title;

  @NotNull private LocalDateTime plannedAt;

  @NotNull private Integer duration;
  private SessionMethod method;
}
