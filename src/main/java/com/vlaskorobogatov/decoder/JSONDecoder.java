package com.vlaskorobogatov.decoder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vlaskorobogatov.libstorage.Rack;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class JSONDecoder<T> implements Decoder<T> {
    @Override
    public List<T> decode(String fileName) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<T>>() {
        }.getType();
        try {
            Reader reader = new FileReader(fileName);
            return gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String encode(List<T> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static void main(String[] args) {
        JSONDecoder<Rack> decoder = new JSONDecoder<>();
        List<Rack> racks = decoder.decode("src/main/resources/test.json");

        System.out.println(racks.getClass());
        System.out.println(racks);
        if(!racks.isEmpty()) {
            System.out.println(racks.get(0).toString());
        }

        System.out.println(decoder.encode(racks));
    }
}
