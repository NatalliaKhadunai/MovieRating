package main.model.exception;

/**
 * Exception, used when user cannot be found.
 */

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super();
    }
    public UserNotFoundException(String message) {
        super(message);
    }
}
