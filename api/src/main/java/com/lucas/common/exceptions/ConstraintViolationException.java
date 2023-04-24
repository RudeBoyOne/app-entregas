package com.lucas.common.exceptions;

import lombok.Getter;

import javax.validation.ConstraintViolation;
import java.io.Serial;
import java.util.Set;

@Getter
public class ConstraintViolationException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private final Set<? extends ConstraintViolation<?>> violations;

    public ConstraintViolationException(Set<? extends ConstraintViolation<?>> violations) {
        this.violations = violations;
    }
}
