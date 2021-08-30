package com.vlaskorobogatov.libstorage;

import java.util.ArrayList;
import java.util.List;

public class Shelf {
    public List<Book> books;

    public Shelf() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }
}