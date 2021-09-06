package com.vlaskorobogatov.libstorage;

import java.util.List;

public class Rack {
    private final int id;
    public List<Shelf> shelves;

    public Rack() {
        id = 1;
    }

    public Rack(int id, List<Shelf> shelves) {
        this.id = id;
        this.shelves = shelves;
    }

    public Rack(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


}
