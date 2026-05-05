package com.agenda.presentation.api.request;

import com.agenda.utils.SessionMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSessionRequest {
    private String title;
    private LocalDateTime plannedAt;
    private Integer duration;
    private SessionMethod method;
}
