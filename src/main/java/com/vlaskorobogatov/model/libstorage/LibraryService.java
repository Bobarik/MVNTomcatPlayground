package com.vlaskorobogatov.model.libstorage;

import java.util.Map;

public interface LibraryService {
    Book getBookById(int bookId);

    void postBook(Book book);

    void deleteBook(int bookId);

    void patchBook(int id, Map<String, String> properties);

    Map<Integer, Book> getBooks(Integer rackId, Integer shelfNumber, String bookName);
}
