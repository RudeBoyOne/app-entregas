package com.lucas.common.exceptions;

import java.io.Serial;

public class ExistingResourceException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public ExistingResourceException(String message) {
        super(message);
    }
}
