package com.agenda.presentation.api.request;

import com.agenda.utils.SessionMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSessionRequest {
    @NotBlank
    private String title;

    @NotNull
    private LocalDateTime plannedAt;

    @NotNull
    private Integer duration;
    private SessionMethod method;
}
