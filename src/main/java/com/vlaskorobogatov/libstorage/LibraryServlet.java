package com.vlaskorobogatov.libstorage;

import com.vlaskorobogatov.decoder.Decoder;
import com.vlaskorobogatov.decoder.JSONDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class LibraryServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        Decoder<Rack> decoder = new JSONDecoder<>();
        log("Method init");
    }
}
