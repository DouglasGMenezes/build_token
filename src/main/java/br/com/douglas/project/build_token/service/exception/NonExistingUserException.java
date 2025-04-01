package br.com.douglas.project.build_token.service.exception;

public class NonExistingUserException extends RuntimeException {

    public NonExistingUserException(String message) {
        super(message);
    }
}
