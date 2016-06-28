package main.model.exception;

import main.model.entity.VideoProduct;

/**
 * Exception, used when video product cannot be found.
 */

public class VideoProductNotFoundException extends RuntimeException {
    public VideoProductNotFoundException() {
        super();
    }
    public VideoProductNotFoundException(String message) {
        super(message);
    }
}
