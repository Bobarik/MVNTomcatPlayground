package com.vlaskorobogatov.controller.exceptions;

public class BookAlreadyExistsException extends LibraryServletException{
    public BookAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
