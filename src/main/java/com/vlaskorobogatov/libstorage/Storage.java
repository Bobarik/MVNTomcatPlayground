package com.vlaskorobogatov.libstorage;

import java.util.Map;

public class Storage {
    private Map<Integer, Book> books;

    public Storage() {}

    public Map<Integer, Book> getBooks() {
        return books;
    }
}
