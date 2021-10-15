package com.vlaskorobogatov.model.decoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonParser implements Parser {
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
    public Object fromJSON(String jsonFile, Class<?> firstClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonFile, firstClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
