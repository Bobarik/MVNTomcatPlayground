package com.vlaskorobogatov.controller.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
