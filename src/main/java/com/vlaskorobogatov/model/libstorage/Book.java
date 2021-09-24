package com.vlaskorobogatov.model.libstorage;

import com.vlaskorobogatov.controller.exceptions.IncorrectParameterException;

public class Book {
    private String name;
    private String author;
    private String description;
    private int rackId;
    private int shelf;

    public void setRackId(int rackId) throws IncorrectParameterException {
        if (rackId < 1) {
            throw new IncorrectParameterException("Rack ID must be a positive number.");
        }
        this.rackId = rackId;
    }

    public void setShelf(int shelf) throws IncorrectParameterException {
        if (shelf > 3 || shelf < 1) {
            throw new IncorrectParameterException("Shelf number must be in range of [1;3]");
        }
        this.shelf = shelf;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Book(String name, String author, String description, int rackId, int shelf) throws IncorrectParameterException {
        this.name = name;
        this.author = author;
        this.description = description;
        this.rackId = rackId;
        if (shelf > 3 || shelf < 1) {
            throw new IncorrectParameterException("Shelf number must be in range of [1;3]");
        }
        this.shelf = shelf;
    }

    public Book() {
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public int getRackId() {
        return rackId;
    }

    public int getShelfNumber() {
        return shelf;
    }

    @Override
    public String toString() {
        return "\nName: " + getName()
                + "\nAuthor: " + getAuthor()
                + "\nDescription: " + getDescription()
                + "\nRackId: " + getRackId()
                + "\nShelf number: " + getShelfNumber() + "\n";
    }
}