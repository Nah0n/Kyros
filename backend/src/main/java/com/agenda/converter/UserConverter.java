package com.agenda.converter;

import com.agenda.data.model.UserModel;
import com.agenda.domain.entity.UserEntity;
import com.agenda.presentation.api.response.UserResponse;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserConverter {
  public UserEntity toEntity(UserModel model) {
    if (model == null) return null;

    UserEntity entity = new UserEntity();
    entity.setId(model.getId());
    entity.setLogin(model.getLogin());
    entity.setPassword(model.getPassword());
    entity.setCreatedAt(model.getCreatedAt());

    return entity;
  }

  public UserModel toModel(UserEntity entity) {
    if (entity == null) return null;

    UserModel model = new UserModel();
    model.setId(entity.getId());
    model.setLogin(entity.getLogin());
    model.setPassword(entity.getPassword());
    model.setCreatedAt(entity.getCreatedAt());

    return model;
  }

  public UserResponse toResponse(UserEntity entity) {
    if (entity == null) return null;

    UserResponse response = new UserResponse();
    response.setId(entity.getId());
    response.setCreatedAt(entity.getCreatedAt());
    response.setLogin(entity.getLogin());

    return response;
  }
}
