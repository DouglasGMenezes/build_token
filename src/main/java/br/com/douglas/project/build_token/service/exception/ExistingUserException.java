package br.com.douglas.project.build_token.service.exception;

public class ExistingUserException extends RuntimeException {

    public ExistingUserException (String message) {
        super(message);
    }
}
