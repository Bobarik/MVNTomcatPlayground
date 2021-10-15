package com.vlaskorobogatov.controller.exceptions;

public class BookNotFoundException extends LibraryServletException {
    public BookNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
