package com.vlaskorobogatov.libstorage;

import java.util.List;

public class Rack {
    private final int id;
    public List<Shelf> shelves;

    public Rack(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


}
