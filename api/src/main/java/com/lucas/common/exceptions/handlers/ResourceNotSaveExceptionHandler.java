package com.lucas.common.exceptions.handlers;

import com.lucas.common.exceptions.Problem;
import com.lucas.common.exceptions.ResourceNotSaveException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.OffsetDateTime;

@Provider
public class ResourceNotSaveExceptionHandler implements ExceptionMapper<ResourceNotSaveException> {

    @Override
    public Response toResponse(ResourceNotSaveException resourceNotSaveException) {
        Problem problem = new Problem();
        problem.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
        problem.setDateTime(OffsetDateTime.now());
        problem.setTitle(resourceNotSaveException.getMessage());

        return Response.status(Response.Status.BAD_REQUEST).entity(problem).build();
    }
}
