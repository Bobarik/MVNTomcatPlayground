package com.vlaskorobogatov.decoder;

public interface Parser {
    String toJSON(Object object);
    Object fromJSON(String jsonFile, Class<?> firstClass);
}
