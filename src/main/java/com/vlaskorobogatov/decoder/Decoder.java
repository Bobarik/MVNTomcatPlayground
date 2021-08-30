package com.vlaskorobogatov.decoder;

import com.google.gson.Gson;

import java.util.List;

public interface Decoder<TypeSign> {
    List<TypeSign> decode(String fileName);
    String encode(List<TypeSign> list);
}