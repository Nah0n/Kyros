package com.agenda.presentation.rest;

import com.agenda.converter.SessionConverter;
import com.agenda.domain.entity.SessionEntity;
import com.agenda.domain.service.SessionService;
import com.agenda.presentation.api.request.CreateSessionRequest;
import com.agenda.presentation.api.request.UpdateSessionRequest;
import com.agenda.presentation.api.response.SessionResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/sessions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SessionResource {
    @Inject
    SessionService sessionService;

    @Inject
    SessionConverter sessionConverter;


    @POST
    public Response create(CreateSessionRequest request) {
        SessionEntity entity = sessionService.create(request.getTitle(), request.getPlannedAt(), request.getDuration(), request.getMethod(), 1L);//TODO userId token
        return Response.status(201).entity(sessionConverter.toResponse(entity)).build();
    }

    @GET
    public Response getAll()
    {
        List<SessionEntity> entities = sessionService.getAll(1L); //TODO userId token
        List<SessionResponse> responses = entities.stream()
                .map(sessionConverter::toResponse)
                .toList();
        return Response.ok(responses).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id)
    {
        SessionEntity entity = sessionService.getById(id);
        return Response.ok(sessionConverter.toResponse(entity)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, UpdateSessionRequest request)
    {
        SessionEntity entity = sessionService.update(id, request.getTitle(), request.getDuration(), request.getPlannedAt(), request.getMethod());
        return Response.ok(sessionConverter.toResponse(entity)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id)
    {
        sessionService.delete(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}/start")
    public Response start(@PathParam("id")Long id)
    {
        SessionEntity entity = sessionService.start(id);
        return Response.ok(sessionConverter.toResponse(entity)).build();
    }

    @PUT
    @Path("/{id}/finish")
    public Response finish(@PathParam("id")Long id)
    {
        SessionEntity entity = sessionService.finish(id);
        return Response.ok(sessionConverter.toResponse(entity)).build();
    }
}
