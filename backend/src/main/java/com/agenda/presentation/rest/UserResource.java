package com.agenda.presentation.rest;

import com.agenda.converter.UserConverter;
import com.agenda.domain.entity.UserEntity;
import com.agenda.domain.service.UserService;
import com.agenda.presentation.api.request.LoginRequest;
import com.agenda.presentation.api.request.RegisterRequest;
import com.agenda.presentation.api.response.AuthResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;
    @Inject
    UserConverter userConverter;

    @POST
    @Path("/register")
    public Response register(RegisterRequest request)
    {
        UserEntity entity = userService.createUser(request.getLogin(), request.getPassword());
        return Response.status(201).entity(userConverter.toResponse(entity)).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest request)
    {
        UserEntity entity = userService.login(request.getLogin(), request.getPassword());
        //TODO: handle token generation
        AuthResponse response = new AuthResponse(null, userConverter.toResponse(entity));

        return Response.ok(response).build();
    }
}
