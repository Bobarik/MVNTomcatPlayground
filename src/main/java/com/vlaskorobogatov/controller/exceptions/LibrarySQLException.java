package com.vlaskorobogatov.controller.exceptions;

public class LibrarySQLException extends LibraryServletException {
    public LibrarySQLException(String errorMessage) {
        super(errorMessage);
    }
}
