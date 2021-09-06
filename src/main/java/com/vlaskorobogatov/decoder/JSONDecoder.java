package com.vlaskorobogatov.decoder;

import com.vlaskorobogatov.libstorage.Book;
import com.vlaskorobogatov.libstorage.Rack;
import com.vlaskorobogatov.libstorage.Shelf;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JSONDecoder implements Decoder {
    String fileName;
    Parser parser;

    public JSONDecoder(Parser parser, String fileName) {
        this.fileName = fileName;
        this.parser = parser;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public Object decode() {
        try {
            String jsonString = new String(Files.readAllBytes(Path.of(fileName)));
            return parser.fromJSON(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String encode(Object list) {
        return parser.toJSON(list);
    }

    public static void main(String[] args) {
        JSONDecoder decoder = new JSONDecoder(new GsonParser(), "src/main/resources/test.json");
        List<Rack> racks = (List<Rack>) decoder.decode();
        for (Rack rack:
                racks) {
            for (Shelf shelf:
                    rack.shelves) {
                for (Book book:
                        shelf.books) {
                    System.out.println((book.toString() + "\n"));
                }
            }
        }
        if(!racks.isEmpty()) {
            System.out.println(racks.get(0).toString());
        }

        System.out.println(decoder.encode(racks));
    }
}
