package com.vlaskorobogatov.libstorage;

public class Book {
    private final int id;
    private String name;
    private String author;
    private String description;

    public Book() {
        id = hashCode();
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

    public Book(int id, String name, String author, String description) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
    }

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return "Id: " + id
                + "\nName: " + name
                + "\nAuthor: " + author
                + "\nDescription: " + description + "\n";
    }
}