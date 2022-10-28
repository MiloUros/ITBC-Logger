package com.example.ITBC.Logger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorMessage userNotFoundException(UserNotFoundException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUsernameException.class)
    public ErrorMessage invalidUsernameException(InvalidUsernameException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ErrorMessage invalidCredentialsException(InvalidCredentialsException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(InvalidObjectException.class)
    public ErrorMessage invalidObjectException(InvalidObjectException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidEmailException.class)
    public ErrorMessage invalidEmailException(InvalidEmailException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorMessage usernameNotFoundException(UsernameNotFoundException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsernameTakenException.class)
    public ErrorMessage usernameTakenException(UsernameTakenException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailTakenException.class)
    public ErrorMessage emailTakenException(EmailTakenException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidPasswordException.class)
    public ErrorMessage invalidPasswordException(InvalidPasswordException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ErrorMessage forbiddenException(ForbiddenException e) {
        return createErrorMessage(e.getMessage());
    }

    private ErrorMessage createErrorMessage(String message) {
        return ErrorMessage.builder().errorMessage(message).build();
    }
}