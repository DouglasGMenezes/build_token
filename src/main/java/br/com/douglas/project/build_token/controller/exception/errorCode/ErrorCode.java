package br.com.douglas.project.build_token.controller.exception.errorCode;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "User is already registered in the system."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User does not exist in the system."),
    INVALID_LOGIN(HttpStatus.BAD_REQUEST, "Try again, incorrect email or password."),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "Without permission.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
