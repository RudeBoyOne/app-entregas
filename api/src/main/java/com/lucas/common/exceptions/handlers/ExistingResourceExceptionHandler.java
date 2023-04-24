package com.lucas.common.exceptions.handlers;

import com.lucas.common.exceptions.ExistingResourceException;
import com.lucas.common.exceptions.Problem;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.OffsetDateTime;

@Provider
public class ExistingResourceExceptionHandler implements ExceptionMapper<ExistingResourceException> {

    @Override
    public Response toResponse(ExistingResourceException existingResourceException) {
        Problem problem = new Problem();
        problem.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
        problem.setDateTime(OffsetDateTime.now());
        problem.setTitle(existingResourceException.getMessage());

        return Response.status(Response.Status.BAD_REQUEST).entity(problem).build();
    }
}
