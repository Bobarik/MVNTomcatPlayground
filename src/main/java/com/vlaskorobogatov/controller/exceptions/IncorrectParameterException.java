package com.vlaskorobogatov.controller.exceptions;

public class IncorrectParameterException extends RuntimeException {
    public IncorrectParameterException(String errorMessage) {
        super(errorMessage);
    }
}
