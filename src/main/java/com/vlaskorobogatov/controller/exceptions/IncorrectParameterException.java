package com.vlaskorobogatov.controller.exceptions;

public class IncorrectParameterException extends LibraryServletException {
    public IncorrectParameterException(String errorMessage) {
        super(errorMessage);
    }
}
