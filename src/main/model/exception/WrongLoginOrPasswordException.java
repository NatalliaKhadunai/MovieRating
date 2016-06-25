package main.model.exception;

/**
 * Exception, used when user entered wrong login or password.
 */

public class WrongLoginOrPasswordException extends RuntimeException {
    public WrongLoginOrPasswordException() {
        super();
    }
    public WrongLoginOrPasswordException(String message) {
        super(message);
    }
}
