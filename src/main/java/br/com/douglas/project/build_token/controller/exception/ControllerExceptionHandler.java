package br.com.douglas.project.build_token.controller.exception;


import br.com.douglas.project.build_token.controller.exception.errorCode.ErrorCode;
import br.com.douglas.project.build_token.service.exception.ExistingUserException;
import br.com.douglas.project.build_token.service.exception.InvalidLoginException;
import br.com.douglas.project.build_token.service.exception.NonExistingUserException;
import br.com.douglas.project.build_token.service.exception.PermissionDeniedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Instant;



@RestControllerAdvice
public class ControllerExceptionHandler {

    private ResponseEntity<ErrorResponse> createErrorResponse(Exception e, ErrorCode errorCode, HttpServletRequest request) {
        ErrorResponse err = new ErrorResponse(Instant.now(), errorCode.getStatus().value(), errorCode.getMessage(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(errorCode.getStatus()).body(err);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(EntityNotFoundException e, HttpServletRequest request) {
        return createErrorResponse(e, ErrorCode.NOT_FOUND, request);
    }

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<ErrorResponse> existingUser(ExistingUserException e, HttpServletRequest request) {
        return createErrorResponse(e, ErrorCode.USER_ALREADY_EXISTS, request);
    }

    @ExceptionHandler(NonExistingUserException.class)
    public ResponseEntity<ErrorResponse> nonExistingUser(NonExistingUserException e, HttpServletRequest request) {
        return createErrorResponse(e, ErrorCode.USER_NOT_FOUND, request);
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ErrorResponse> invalidLogin(InvalidLoginException e, HttpServletRequest request) {
        return createErrorResponse(e, ErrorCode.INVALID_LOGIN, request);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ErrorResponse> permissionDenied(PermissionDeniedException e, HttpServletRequest request) {
        return createErrorResponse(e, ErrorCode.PERMISSION_DENIED, request);
    }
}
