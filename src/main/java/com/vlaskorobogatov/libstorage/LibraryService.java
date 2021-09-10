package com.vlaskorobogatov.libstorage;

import java.util.Map;
import java.util.stream.Collectors;

public class LibraryService {

    private final Storage storage;

    public Storage getStorage() {
        return storage;
    }

    public LibraryService(Storage storage) {
        this.storage = storage;
    }

    public Book getBookById(int id) {
        return storage.getBooks().get(id);
    }

    public Map<Integer, Book> getBooksByRack(int rackId) {
        return storage.getBooks().entrySet()
                .stream()
                .filter(integerBookEntry -> integerBookEntry.getValue().getRackId() == rackId)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Integer, Book> getBooksByRack(int rackId, int shelfNumber) {
        return storage.getBooks().entrySet()
                .stream()
                .filter(bookEntry -> bookEntry.getValue().getRackId() == rackId)
                .filter(bookEntry -> bookEntry.getValue().getShelfNumber() == shelfNumber)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Integer, Book> getBooksByShelf(int shelfNumber) {
        return storage.getBooks().entrySet()
                .stream()
                .filter(bookEntry -> bookEntry.getValue().getShelfNumber() == shelfNumber)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void putBook(Book book, int bookId) {
        storage.getBooks().put(bookId, book);
    }

    public void deleteBook(int bookId) throws IllegalArgumentException{
        Book book = storage.getBooks().remove(bookId);
        if(book == null) {
            throw new IllegalArgumentException("Can't delete the book with this ID because it doesn't exist.");
        }
    }

    public Map<Integer, Book> getBookByName(String name) {
        return storage.getBooks().entrySet()
                .stream()
                .filter(bookEntry -> bookEntry.getValue().getName().equals(name))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void patchBook(int id, Map<String, String> properties) throws NumberFormatException {
        Book book = storage.getBooks().get(id);
        if (properties.get("name") != null) {
            book.setName(properties.get("name"));
        }
        if (properties.get("author") != null) {
            book.setAuthor(properties.get("author"));
        }
        if (properties.get("description") != null) {
            book.setDescription(properties.get("description"));
        }
        if (properties.get("shelf") != null) {
            book.setShelfNumber(Integer.parseInt(properties.get("shelf")));
        }
        if (properties.get("rackId") != null) {
            book.setRackId(Integer.parseInt(properties.get("rackId")));
        }
    }
}