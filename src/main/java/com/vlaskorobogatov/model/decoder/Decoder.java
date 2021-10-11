package com.vlaskorobogatov.model.decoder;

public interface Decoder {
    Object decode();

    String encode(Object list);
}
