package com.agenda.data.repository;

import com.agenda.data.model.UserModel;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<UserModel, Long> {
}
