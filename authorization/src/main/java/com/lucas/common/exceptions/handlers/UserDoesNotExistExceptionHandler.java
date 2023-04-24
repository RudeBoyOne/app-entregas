package com.lucas.common.exceptions.handlers;

import com.lucas.common.exceptions.Problem;
import com.lucas.common.exceptions.UserDoesNotExistException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.time.OffsetDateTime;

public class UserDoesNotExistExceptionHandler implements ExceptionMapper<UserDoesNotExistException> {
    @Override
    public Response toResponse(UserDoesNotExistException e) {
        Problem problem = new Problem();

        problem.setTitle(e.getMessage());
        problem.setDateTime(OffsetDateTime.now());
        problem.setStatus(Response.Status.NOT_FOUND.getStatusCode());

        return Response.status(Response.Status.NOT_FOUND).entity(problem).build();
    }
}
