package com.agenda.domain.service;

import com.agenda.converter.UserConverter;
import com.agenda.data.model.UserModel;
import com.agenda.data.repository.UserRepository;
import com.agenda.domain.entity.UserEntity;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

@ApplicationScoped
public class UserService {
  @Inject UserRepository userRepository;

  @Inject UserConverter userConverter;

  @Transactional
  public UserEntity createUser(String login, String password) {
    // TODO: handle blank login in Request

    if (userRepository.findByLogin(login) != null)
      throw new RuntimeException("login already taken");

    UserModel model = new UserModel();
    model.setLogin(login);
    model.setPassword(BcryptUtil.bcryptHash(password));
    model.setCreatedAt(LocalDateTime.now());
    userRepository.persist(model);
    return userConverter.toEntity(model);
  }

  public UserEntity login(String login, String password) {
    UserModel model = userRepository.findByLogin(login);

    if (model == null) throw new RuntimeException("User not found");

    if (!BcryptUtil.matches(password, model.getPassword()))
      throw new RuntimeException("Invalid Password");

    return userConverter.toEntity(model);
  }
}
