package com.lucas.common.exceptions.handlers;

import com.lucas.common.exceptions.AuthorizationException;
import com.lucas.common.exceptions.Problem;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.OffsetDateTime;

@Provider
public class AuthotizationExceptionHandler implements ExceptionMapper<AuthorizationException> {

    @Override
    public Response toResponse(AuthorizationException e) {
        Problem problem = new Problem();

        problem.setTitle(e.getMessage());
        problem.setDateTime(OffsetDateTime.now());
        problem.setStatus(Response.Status.BAD_REQUEST.getStatusCode());

        return Response.status(Response.Status.BAD_REQUEST).entity(problem).build();
    }

}
