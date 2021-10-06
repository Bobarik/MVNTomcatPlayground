package com.vlaskorobogatov.controller.exceptions;

public class InvalidUriException extends RuntimeException {
    public InvalidUriException(String errorMessage) {
        super(errorMessage);
    }
}
