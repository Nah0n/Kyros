package com.agenda.data.repository;

import com.agenda.data.model.TaskModel;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskRepository implements PanacheRepositoryBase<TaskModel, Long> {}
