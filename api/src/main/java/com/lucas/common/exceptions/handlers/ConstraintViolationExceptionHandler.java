package com.lucas.common.exceptions.handlers;

import com.lucas.common.exceptions.ConstraintViolationException;
import com.lucas.common.exceptions.Problem;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        Problem problem = new Problem();


        return null;
    }
}
