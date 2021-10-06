package com.vlaskorobogatov.model.libstorage;

import com.vlaskorobogatov.controller.exceptions.BookNotFoundException;
import com.vlaskorobogatov.controller.exceptions.IncorrectParameterException;

import java.util.Map;
import java.util.function.Predicate;
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

    public void putBook(Book book, int bookId) {
        storage.getBooks().put(bookId, book);
    }

    public void deleteBook(int bookId) throws BookNotFoundException {
        Book book = storage.getBooks().remove(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book with this ID doesn't exist");
        }
    }

    public void patchBook(int id, Map<String, String> properties) throws IncorrectParameterException {
        try {
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
                book.setShelf(Integer.parseInt(properties.get("shelf")));
            }
            if (properties.get("rackId") != null) {
                book.setRackId(Integer.parseInt(properties.get("rackId")));
            }
        } catch (NumberFormatException e) {
            throw new IncorrectParameterException("Incorrect parameter: shelf, rackId should be integers.");
        }
    }

    public Map<Integer, Book> getBooks(Integer rackId, Integer shelfNumber, String bookName) {
        Map<Integer, Book> books = storage.getBooks();

        books = books.entrySet().stream()
                .filter(buildEntryFilter(rackId, shelfNumber, bookName))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return books;
    }

    private static Predicate<Map.Entry<Integer, Book>> buildEntryFilter(Integer rackId, Integer shelfNumber, String bookName) {
        Predicate<Map.Entry<Integer, Book>> predicate = i -> true;
        if (rackId != null) {
            predicate = predicate.and(i -> (i.getValue().getRackId() == rackId));
        }
        if (shelfNumber != null) {
            predicate = predicate.and(i -> (i.getValue().getShelfNumber() == shelfNumber));
        }
        if (bookName != null) {
            predicate = predicate.and(i -> (i.getValue().getName().equals(bookName)));
        }

        return predicate;
    }
}