package com.example.ITBC.Logger.Exceptions;

public class InvalidUsernameException extends RuntimeException{

    public InvalidUsernameException(String message) {
        super(message);
    }

    public InvalidUsernameException(String message, Throwable cause) {
        super(message, cause);
    }
}

