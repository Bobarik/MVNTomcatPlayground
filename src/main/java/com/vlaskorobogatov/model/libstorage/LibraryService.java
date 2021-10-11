package com.vlaskorobogatov.model.libstorage;

import com.vlaskorobogatov.controller.exceptions.LibraryServletException;

import java.util.Map;

public interface LibraryService {
    Book getBookById(int bookId) throws LibraryServletException;

    void postBook(Book book) throws LibraryServletException;

    void deleteBook(int bookId) throws LibraryServletException;

    void patchBook(int id, Map<String, String> properties) throws LibraryServletException;

    Map<Integer, Book> getBooks(Integer rackId, Integer shelfNumber, String bookName) throws LibraryServletException;
}
