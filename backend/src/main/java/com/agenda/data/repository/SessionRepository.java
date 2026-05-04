package com.agenda.data.repository;

import com.agenda.data.model.SessionModel;
import com.agenda.data.model.UserModel;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class SessionRepository implements PanacheRepositoryBase<SessionModel, Long> {
    public List<SessionModel> findByUser(UserModel user) {
        return find("user", user).list();
    }
}