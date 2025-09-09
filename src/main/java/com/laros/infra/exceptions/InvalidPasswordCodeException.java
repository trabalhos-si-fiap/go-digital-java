package com.laros.infra.exceptions;

public class InvalidPasswordCodeException extends RuntimeException {
    public InvalidPasswordCodeException() {
        super();
    }

    public InvalidPasswordCodeException(String message) {
        super(message);
    }
}
