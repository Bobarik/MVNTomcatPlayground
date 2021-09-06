package com.vlaskorobogatov.decoder;

public interface Decoder {
    Object decode();
    String encode(Object list);
}