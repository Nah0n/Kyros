package com.agenda.data.repository;

import com.agenda.data.model.SessionModel;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SessionRepository implements PanacheRepositoryBase<SessionModel, Long> {
}
