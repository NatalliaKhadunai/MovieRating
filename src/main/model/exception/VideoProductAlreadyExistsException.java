package main.model.exception;

/**
 * Exception, used when administrator tried to add video product that already exists.
 */

public class VideoProductAlreadyExistsException extends RuntimeException {
    public VideoProductAlreadyExistsException() {
        super();
    }
    public VideoProductAlreadyExistsException(String message) {
        super(message);
    }
}
