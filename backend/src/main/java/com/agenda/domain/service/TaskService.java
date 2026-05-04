package com.agenda.domain.service;

import com.agenda.converter.TaskConverter;
import com.agenda.data.model.SessionModel;
import com.agenda.data.model.TaskModel;
import com.agenda.data.repository.SessionRepository;
import com.agenda.data.repository.TaskRepository;
import com.agenda.domain.entity.TaskEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TaskService {
    @Inject
    TaskRepository taskRepository;

    @Inject
    TaskConverter taskConverter;

    @Inject
    SessionRepository sessionRepository;

    @Transactional
    public TaskEntity create (String title, Long sessionId)
    {
        //TODO: handle blank title in Request
        TaskModel model = new TaskModel();
        model.setTitle(title);
        model.setDone(false);

        SessionModel session = sessionRepository.findById(sessionId);
        if (session == null)
            throw new RuntimeException("Session not found");

        model.setSession(session);
        taskRepository.persist(model);
        return taskConverter.toEntity(model);
    }

    @Transactional
    public TaskEntity update(String title, Long taskId)
    {
        TaskModel model = taskRepository.findById(taskId);
        if (model == null)
            throw new RuntimeException("Task not found");
        model.setTitle(title);
        taskRepository.persist(model);
        return taskConverter.toEntity(model);
    }

    @Transactional
    public void delete(Long taskId)
    {
        taskRepository.deleteById(taskId);
    }

    @Transactional
    public TaskEntity toggleDone(Long taskId)
    {
        TaskModel model = taskRepository.findById(taskId);
        if (model == null)
            throw new RuntimeException("Task not found");
        model.setDone(!model.getDone());
        taskRepository.persist(model);
        return taskConverter.toEntity(model);
    }
}
