package com.lucas.common.exceptions;

import java.io.Serial;

public class UserDoesNotExistException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public UserDoesNotExistException(String message) {
        super(message);
    }
}
