package com.vlaskorobogatov.decoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import com.vlaskorobogatov.libstorage.Rack;

import java.lang.reflect.Type;
import java.util.List;

public class JacksonParser implements Parser{
    @Override
    public String toJSON(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object fromJSON(String jsonFile) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonFile, new TypeReference<List<Rack>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}