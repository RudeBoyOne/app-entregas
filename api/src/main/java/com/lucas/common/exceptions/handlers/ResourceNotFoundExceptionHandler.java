package com.lucas.common.exceptions.handlers;

import com.lucas.common.exceptions.Problem;
import com.lucas.common.exceptions.ResourceNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.OffsetDateTime;

@Provider
public class ResourceNotFoundExceptionHandler implements ExceptionMapper<ResourceNotFoundException> {


    @Override
    public Response toResponse(ResourceNotFoundException resourceNotFoundException) {
        Problem problem = new Problem();
        problem.setStatus(Response.Status.NOT_FOUND.getStatusCode());
        problem.setDateTime(OffsetDateTime.now());
        problem.setTitle(resourceNotFoundException.getMessage());

        return Response.status(Response.Status.NOT_FOUND).entity(problem).build();
    }
}
