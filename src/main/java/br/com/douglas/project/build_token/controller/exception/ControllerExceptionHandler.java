package br.com.douglas.project.build_token.controller.exception;


import br.com.douglas.project.build_token.controller.exception.errorCode.ErrorCode;
import br.com.douglas.project.build_token.service.exception.ExistingUserException;
import br.com.douglas.project.build_token.service.exception.InvalidLoginException;
import br.com.douglas.project.build_token.service.exception.NonExistingUserException;
import br.com.douglas.project.build_token.service.exception.PermissionDeniedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Instant;
import org.springframework.http.HttpStatus;


@RestControllerAdvice
public class ControllerExceptionHandler {

    private ResponseEntity<ErrorResponse> createErrorResponse(Exception e, HttpStatus status, String error, HttpServletRequest request) {
        ErrorResponse err = new ErrorResponse(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(EntityNotFoundException e, HttpServletRequest request) {
        return createErrorResponse(e, HttpStatus.NOT_FOUND, "Not Found", request);
    }

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<ErrorResponse> ExistingUser(ExistingUserException e, HttpServletRequest request) {
        return createErrorResponse(e, HttpStatus.CONFLICT, "User is already registered in the system.", request);
    }

    @ExceptionHandler(NonExistingUserException.class)
    public ResponseEntity<ErrorResponse> NonExistingUser(NonExistingUserException e, HttpServletRequest request) {
        return createErrorResponse(e, HttpStatus.NOT_FOUND, "User does not exist in the system.", request);
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ErrorResponse> InvalidLogin(InvalidLoginException e, HttpServletRequest request) {
        return createErrorResponse(e, HttpStatus.BAD_REQUEST, "Try again, incorrect email or password.", request);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ErrorResponse> PermissionDenied(PermissionDeniedException e, HttpServletRequest request) {
        return createErrorResponse(e, HttpStatus.FORBIDDEN, "Without permission.", request);
    }
}
