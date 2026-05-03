package com.agenda.data.model;

import com.agenda.utils.SessionMethod;
import com.agenda.utils.SessionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sessions")
public class SessionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private LocalDateTime plannedAt;
    private Integer duration;

    @Enumerated(EnumType.STRING)
    private SessionMethod method;

    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<TaskModel> tasks;
}
