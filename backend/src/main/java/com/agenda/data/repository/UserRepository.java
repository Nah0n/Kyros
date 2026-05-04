package com.agenda.data.repository;

import com.agenda.data.model.UserModel;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<UserModel, Long> {
    public UserModel findByLogin(String login) {
        return find("login", login).firstResult();
    }

}