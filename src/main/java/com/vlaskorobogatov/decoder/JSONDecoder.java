package com.vlaskorobogatov.decoder;

import com.vlaskorobogatov.libstorage.Storage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

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
            return parser.fromJSON(jsonString, Storage.class);
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
        JSONDecoder decoder = new JSONDecoder(new GsonParser(), "src/main/resources/booksonly.json");
        Storage storage = (Storage) decoder.decode();

        storage.getBooks().entrySet()
                .stream()
                .filter(integerBookEntry -> integerBookEntry.getValue().getRackId() == 12)
                .forEach(System.out::println);
    }
}