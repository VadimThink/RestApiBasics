package com.epam.esm.exception;

/**
 * this class may be needed later
 **/
public class InvalidParametersException extends Throwable {

    public InvalidParametersException() {
    }

    public InvalidParametersException(String message) {
        super(message);
    }

    public InvalidParametersException(String message, Throwable cause) {
        super(message, cause);
    }
}
