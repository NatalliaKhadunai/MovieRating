package main.model.exception;

public class WrongLoginOrPasswordException extends RuntimeException {
    public WrongLoginOrPasswordException() {
        super();
    }
    public WrongLoginOrPasswordException(String message) {
        super(message);
    }
}
