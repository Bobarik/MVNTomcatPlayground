package com.vlaskorobogatov.libstorage;

public class Book {
    private String name;
    private String author;
    private String description;
    private int rackId;
    private int shelf;

    public void setRackId(int rackId) {
        this.rackId = rackId;
    }

    public void setShelfNumber(int shelf) throws NumberFormatException {
        if(shelf > 3 || shelf < 1) {
            throw new NumberFormatException("Shelf number must be in range of [1;3]");
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

    public Book(String name, String author, String description, int rackId, int shelf) throws NumberFormatException {
        this.name = name;
        this.author = author;
        this.description = description;
        this.rackId = rackId;
        if(shelf > 3 || shelf < 1) {
            throw new NumberFormatException("Shelf number must be in range of [1;3]");
        }
        this.shelf = shelf;
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