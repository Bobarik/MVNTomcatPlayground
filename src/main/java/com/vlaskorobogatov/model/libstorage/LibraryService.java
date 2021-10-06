package com.vlaskorobogatov.model.libstorage;

import com.vlaskorobogatov.controller.exceptions.BookNotFoundException;
import com.vlaskorobogatov.controller.exceptions.IncorrectParameterException;

import java.util.Map;

public interface LibraryService {
    Book getBookById(int bookId);

    void postBook(Integer bookId, Book book);

    void deleteBook(int bookId) throws BookNotFoundException;

    void patchBook(int id, Map<String, String> properties) throws IncorrectParameterException;

    Map<Integer, Book> getBooks(Integer rackId, Integer shelfNumber, String bookName);
}
