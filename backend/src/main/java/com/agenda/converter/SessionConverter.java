package com.agenda.converter;

import com.agenda.data.model.SessionModel;
import com.agenda.data.model.TaskModel;
import com.agenda.domain.entity.SessionEntity;
import com.agenda.domain.entity.TaskEntity;
import com.agenda.presentation.api.response.SessionResponse;
import com.agenda.presentation.api.response.TaskResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SessionConverter {
    @Inject
    TaskConverter taskConverter;

    @Inject
    UserConverter userConverter;

    public SessionEntity toEntity(SessionModel model) {
        if (model == null)
            return null;

        SessionEntity entity = new SessionEntity();

        entity.setId(model.getId());
        entity.setTitle(model.getTitle());
        entity.setPlannedAt(model.getPlannedAt());
        entity.setDuration(model.getDuration());
        entity.setMethod(model.getMethod());
        entity.setStatus(model.getStatus());
        entity.setUser(userConverter.toEntity(model.getUser()));

        List<TaskEntity> tasks = new ArrayList<>();
        if (model.getTasks() != null)
            for (TaskModel task : model.getTasks()) {
                tasks.add(taskConverter.toEntity(task));
            }
        entity.setTasks(tasks);

        return entity;
    }

    public SessionModel toModel(SessionEntity entity) {
        if (entity == null)
            return null;

        SessionModel model = new SessionModel();

        model.setId(entity.getId());
        model.setTitle(entity.getTitle());
        model.setPlannedAt(entity.getPlannedAt());
        model.setDuration(entity.getDuration());
        model.setMethod(entity.getMethod());
        model.setStatus(entity.getStatus());
        model.setUser(userConverter.toModel(entity.getUser()));
        List<TaskModel> tasks = new ArrayList<>();
        if (entity.getTasks() != null)
            for (TaskEntity task : entity.getTasks()) {
                tasks.add(taskConverter.toModel(task));
            }
        model.setTasks(tasks);

        return model;
    }

    public SessionResponse toResponse(SessionEntity entity) {
        if (entity == null)
            return null;

        SessionResponse response = new SessionResponse();

        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setPlannedAt(entity.getPlannedAt());
        response.setDuration(entity.getDuration());
        response.setMethod(entity.getMethod());
        response.setStatus(entity.getStatus());
        response.setUser(userConverter.toResponse(entity.getUser()));
        List<TaskResponse> tasks = new ArrayList<>();
        if (entity.getTasks() != null)
            for (TaskEntity task : entity.getTasks()) {
                tasks.add(taskConverter.toResponse(task));
            }
        response.setTasks(tasks);

        return response;
    }

}
