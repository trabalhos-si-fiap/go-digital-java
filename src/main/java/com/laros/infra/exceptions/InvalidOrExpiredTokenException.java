package com.laros.infra.exceptions;

public class InvalidOrExpiredTokenException extends RuntimeException {
    public InvalidOrExpiredTokenException() {
        super();
    }

    public InvalidOrExpiredTokenException(String message) {
        super(message);
    }
}
