package com.agenda.converter;

import com.agenda.data.model.TaskModel;
import com.agenda.domain.entity.TaskEntity;
import com.agenda.presentation.api.response.TaskResponse;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskConverter {
    public TaskEntity toEntity(TaskModel model) {
        if (model == null)
            return null;

        TaskEntity entity = new TaskEntity();
        entity.setId(model.getId());
        entity.setTitle(model.getTitle());
        entity.setDone(model.getDone());
        return entity;
    }

    public TaskModel toModel(TaskEntity entity) {
        if (entity == null)
            return null;

        TaskModel model = new TaskModel();
        model.setId(entity.getId());
        model.setTitle(entity.getTitle());
        model.setDone(entity.getDone());
        return model;
    }

    public TaskResponse toResponse(TaskEntity entity)
    {
        if (entity == null)
            return null;

        TaskResponse response = new TaskResponse();

        response.setId(entity.getId());
        response.setDone(entity.getDone());
        response.setTitle(entity.getTitle());

        return response;
    }
}
