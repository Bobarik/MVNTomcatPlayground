package com.vlaskorobogatov.model.decoder;

import com.vlaskorobogatov.model.libstorage.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JSONDecoder implements Decoder {
    String fileName;
    Parser parser;

    public JSONDecoder(Parser parser, String fileName) {
        this.fileName = fileName;
        this.parser = parser;
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
}
