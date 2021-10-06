package com.vlaskorobogatov.controller.exceptions;

public class BookAlreadyExistsException extends RuntimeException{
    public BookAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
