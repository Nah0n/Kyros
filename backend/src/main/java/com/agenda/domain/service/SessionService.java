package com.agenda.domain.service;

import com.agenda.converter.SessionConverter;
import com.agenda.data.model.SessionModel;
import com.agenda.data.model.UserModel;
import com.agenda.data.repository.SessionRepository;
import com.agenda.data.repository.UserRepository;
import com.agenda.domain.entity.SessionEntity;
import com.agenda.utils.SessionMethod;
import com.agenda.utils.SessionStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class SessionService {
    @Inject
    SessionRepository sessionRepository;

    @Inject
    SessionConverter sessionConverter;

    @Inject
    UserRepository userRepository;

    @Transactional
    public SessionEntity create(String title, LocalDateTime plannedAt, Integer duration, SessionMethod method, Long userId) {
        //TODO: handle blank title in Request

        SessionModel model = new SessionModel();
        model.setTitle(title);
        model.setPlannedAt(plannedAt);
        model.setDuration(duration);
        model.setMethod(method);
        model.setStatus(SessionStatus.PLANNED);
        UserModel user = userRepository.findById(userId);
        if (user == null)
            throw new RuntimeException("User not found");

        model.setUser(user);
        sessionRepository.persist(model);
        return sessionConverter.toEntity(model);
    }

    public List<SessionEntity> getAll(Long userId) {
        UserModel user = userRepository.findById(userId);
        if (user == null)
            throw new RuntimeException("User not found");

        return sessionRepository.findByUser(user)
                .stream()
                .map(sessionConverter::toEntity)
                .toList();
    }

    public SessionEntity getById(Long sessionId) {
        SessionModel model = sessionRepository.findById(sessionId);
        if (model == null)
            throw new RuntimeException("Session not found");

        return sessionConverter.toEntity(model);
    }

    @Transactional
    public SessionEntity update(Long sessionId, String title, Integer duration, LocalDateTime plannedAt, SessionMethod method) {
        SessionModel model = sessionRepository.findById(sessionId);
        if (model == null)
            throw new RuntimeException("Session not found");
        if (title != null)
            model.setTitle(title);

        if (duration != null)
            model.setDuration(duration);

        if (plannedAt != null)
            model.setPlannedAt(plannedAt);

        if (method != null)
            model.setMethod(method);

        sessionRepository.persist(model);
        return sessionConverter.toEntity(model);
    }

    @Transactional
    public void delete(Long sessionId) {
        SessionModel model = sessionRepository.findById(sessionId);
        if (model == null)
            throw new RuntimeException("Session not found");

        sessionRepository.delete(model);
    }

    @Transactional
    public SessionEntity start(Long sessionId) {
        SessionModel model = sessionRepository.findById(sessionId);
        if (model == null)
            throw new RuntimeException("Session not found");

        if (model.getStatus() == SessionStatus.DONE)
            throw new RuntimeException("Session already done");

        if (model.getStatus() == SessionStatus.IN_PROGRESS)
            throw new RuntimeException("Session already started");

        model.setStatus(SessionStatus.IN_PROGRESS);
        sessionRepository.persist(model);
        return sessionConverter.toEntity(model);
    }

    @Transactional
    public SessionEntity finish(Long sessionId) {
        SessionModel model = sessionRepository.findById(sessionId);
        if (model == null)
            throw new RuntimeException("Session not found");

        if (model.getStatus() == SessionStatus.DONE)
            throw new RuntimeException("Session already done");

        if (model.getStatus() == SessionStatus.PLANNED)
            throw new RuntimeException("Session not started yet");
        model.setStatus(SessionStatus.DONE);
        sessionRepository.persist(model);
        return sessionConverter.toEntity(model);
    }
}