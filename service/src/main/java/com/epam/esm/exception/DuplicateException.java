package com.epam.esm.exception;

public class DuplicateException extends Exception {
    public DuplicateException() {
    }

    public DuplicateException(String message) {
        super(message);
    }
}
