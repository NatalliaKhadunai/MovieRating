package main.model.exception;

/**
 * Exception, used when user wants to use wrong image format as profile photo.
 */

public class ImageFormatException extends RuntimeException {
    public ImageFormatException() {
        super();
    }
    public ImageFormatException(String message) {
        super(message);
    }
}
