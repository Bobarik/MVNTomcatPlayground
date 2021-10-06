package com.vlaskorobogatov.controller.exceptions;

public class JsonResponse {
    private final int errorCode;
    private final Exception exception;

    public String getMessage() {
        return exception.getMessage();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public JsonResponse(int errorCode, Exception exception) {
        this.exception = exception;
        this.errorCode = errorCode;
    }
}
