package com.agenda.presentation.api.response;

import com.agenda.utils.SessionMethod;
import com.agenda.utils.SessionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionResponse {
    private Long id;
    private String title;
    private LocalDateTime plannedAt;
    private Integer duration;
    private SessionMethod method;
    private SessionStatus status;
    private UserResponse user;
    private List<TaskResponse> tasks;
}
