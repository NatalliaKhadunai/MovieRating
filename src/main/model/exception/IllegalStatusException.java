package main.model.exception;

public class IllegalStatusException extends RuntimeException{
    public IllegalStatusException() {
        super();
    }
    public IllegalStatusException(String message) {
        super(message);
    }
}
