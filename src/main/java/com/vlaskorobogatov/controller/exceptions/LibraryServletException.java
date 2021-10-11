package com.vlaskorobogatov.controller.exceptions;

import javax.servlet.ServletException;

public class LibraryServletException extends ServletException {
    public LibraryServletException(String errorMessage) {
        super(errorMessage);
    }
}
