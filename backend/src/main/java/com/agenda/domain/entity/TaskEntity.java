package com.agenda.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskEntity {
    private Long id;
    private String title;
    private Boolean done;
    private SessionEntity session;
    //TODO: Update when TaskModel updated
}
