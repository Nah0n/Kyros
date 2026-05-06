package com.agenda.presentation.rest;

import com.agenda.converter.TaskConverter;
import com.agenda.domain.entity.TaskEntity;
import com.agenda.domain.service.TaskService;
import com.agenda.presentation.api.request.CreateTaskRequest;
import com.agenda.presentation.api.request.UpdateTaskRequest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/sessions/{sessionId}/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("**")
public class TaskResource {
  @Inject TaskService taskService;

  @Inject TaskConverter taskConverter;

  @POST
  public Response create(CreateTaskRequest request, @PathParam("sessionId") Long sessionId) {
    TaskEntity entity = taskService.create(request.getTitle(), sessionId);
    return Response.status(201).entity(taskConverter.toResponse(entity)).build();
  }

  @PUT
  @Path("/{taskId}")
  public Response update(@PathParam("taskId") Long taskId, UpdateTaskRequest request) {
    TaskEntity entity = taskService.update(request.getTitle(), taskId);
    return Response.ok(taskConverter.toResponse(entity)).build();
  }

  @DELETE
  @Path("/{taskId}")
  public Response delete(@PathParam("taskId") Long taskId) {
    taskService.delete(taskId);
    return Response.noContent().build();
  }

  @PUT
  @Path("/{taskId}/toggle")
  public Response toggleDone(@PathParam("taskId") Long taskId) {
    TaskEntity entity = taskService.toggleDone(taskId);
    return Response.ok(taskConverter.toResponse(entity)).build();
  }
}
