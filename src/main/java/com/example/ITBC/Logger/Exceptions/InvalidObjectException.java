package com.example.ITBC.Logger.Exceptions;

public class InvalidObjectException extends RuntimeException {
    public InvalidObjectException(String message) {
        super(message);
    }

    public InvalidObjectException(String message, Throwable cause) {
        super(message, cause);
    }
}

