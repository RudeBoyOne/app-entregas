package com.lucas.common.exceptions;

import java.io.Serial;

public class ResourceNotSaveException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceNotSaveException(String message) {
        super(message);
    }
}
