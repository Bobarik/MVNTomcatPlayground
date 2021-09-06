package com.vlaskorobogatov.libstorage;

import com.vlaskorobogatov.decoder.Decoder;
import com.vlaskorobogatov.decoder.GsonParser;
import com.vlaskorobogatov.decoder.JSONDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@WebServlet("/library/*")
public class LibraryServlet extends HttpServlet {
    List<Rack> rackList;
    Decoder decoder;

    @Override
    public void init() {
        String path = getServletContext().getRealPath("/") + "WEB-INF/classes/test.json";
        decoder = new JSONDecoder(new GsonParser(), path);
        rackList = (List<Rack>) decoder.decode();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        for (Rack rack: rackList) {
            for (Shelf shelf: rack.shelves) {
                for (Book book: shelf.books) {
                    resp.getWriter().write(book.toString() + "\n");
                }
            }
        }
    }

    @Override
    public void destroy() {
        try {
            FileWriter writer = new FileWriter(((JSONDecoder)decoder).getFileName());
            writer.write(decoder.encode(rackList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}