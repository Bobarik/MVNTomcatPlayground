package com.vlaskorobogatov.controller.exceptions;

public class InvalidUriException extends LibraryServletException {
    public InvalidUriException(String errorMessage) {
        super(errorMessage);
    }
}
