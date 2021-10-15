package com.vlaskorobogatov.controller.exceptions;

public class LibraryServletException extends RuntimeException {
    public LibraryServletException(String errorMessage) {
        super(errorMessage);
    }
}
