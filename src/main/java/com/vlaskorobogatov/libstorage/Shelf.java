package com.vlaskorobogatov.libstorage;

import java.util.ArrayList;
import java.util.List;

public class Shelf {
    public final int level;
    public List<Book> books;

    public Shelf(int level) {
        this.level = level;
        books = new ArrayList<>();
    }

    public Shelf() {
        level = 1;
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }
}