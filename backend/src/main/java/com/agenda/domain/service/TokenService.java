package com.agenda.domain.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;

@ApplicationScoped
public class TokenService {

    public String generate(Long userId, String login) {
        return Jwt.issuer("kyros-app")
                .subject(String.valueOf(userId))
                .claim("login", login)
                .expiresIn(Duration.ofHours(24))
                .sign();
    }
}