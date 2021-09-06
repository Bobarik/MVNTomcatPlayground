package com.vlaskorobogatov.decoder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vlaskorobogatov.libstorage.Rack;

import java.lang.reflect.Type;
import java.util.List;

public class GsonParser implements Parser {

    @Override
    public String toJSON(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    @Override
    public Object fromJSON(String jsonFile) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Rack>>(){}.getType();
        return gson.fromJson(jsonFile, listType);
    }
}
