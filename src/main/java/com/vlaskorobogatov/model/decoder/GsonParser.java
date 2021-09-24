package com.vlaskorobogatov.model.decoder;

import com.google.gson.Gson;

public class GsonParser implements Parser {

    @Override
    public String toJSON(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    @Override
    public Object fromJSON(String jsonFile, Class<?> firstClass) {
        Gson gson = new Gson();
        return gson.fromJson(jsonFile, firstClass);
    }
}
