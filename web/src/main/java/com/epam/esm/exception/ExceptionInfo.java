package com.epam.esm.exception;

public class ExceptionInfo {
    private final String errorMessage;
    private final int errorCode;


    public ExceptionInfo(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
