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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/library/*")
public class LibraryServlet extends HttpServlet {
    LibraryService service;
    Decoder decoder;
    String path;

    @Override
    public void init() {
        System.out.println("Initializing servlet");
        path = getServletContext().getRealPath("/") + "WEB-INF/booksonly.json";
        decoder = new JSONDecoder(new GsonParser(), path);
        service = new LibraryService((Storage) decoder.decode());

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equalsIgnoreCase("PATCH")){
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int bookId = Integer.parseInt(req.getParameter("bookId"));
            service.deleteBook(bookId);
        } catch (IllegalArgumentException e) {
            resp.sendError(400, e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String operation = req.getParameter("operation");

        try {
            if(operation == null) {
                throw new IllegalArgumentException("Operation not found");
            }
            switch (operation) {
                case "getBook": {
                    int bookId = Integer.parseInt(req.getParameter("bookId"));
                    Book book = service.getBookById(bookId);
                    if(book == null) {
                        throw new IllegalArgumentException("We couldn't find a book with such ID number.");
                    }
                    resp.getWriter().write(decoder.encode(book));
                    break;
                }
                case "getBooks": {
                    String rackId = req.getParameter("rackId");
                    String shelfNumber = req.getParameter("shelfNumber");
                    String bookName = req.getParameter("name");
                    Map<Integer, Book> books;
                    if(rackId == null) {
                        if(shelfNumber == null) {
                            if(bookName == null) {
                                throw new IllegalArgumentException("Can't search for books without any info :(");
                            }
                            books = service.getBookByName(bookName);
                        } else {
                            books = service.getBooksByShelf(Integer.parseInt(shelfNumber));
                        }
                    } else {
                        if (shelfNumber == null) {
                            books = service.getBooksByRack(Integer.parseInt(rackId));
                        } else {
                            books = service.getBooksByRack(Integer.parseInt(rackId),
                                    Integer.parseInt(shelfNumber));
                        }
                    }
                    if(books.isEmpty()) {
                        throw new IllegalArgumentException("No books found with such arguments.");
                    }
                    resp.getWriter().write(decoder.encode(books));
                    break;
                }
                default:
                    throw new IllegalArgumentException("Operation not found");
            }
        } catch (IllegalArgumentException e) {
            resp.sendError(400, e.getMessage());
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int bookId = Integer.parseInt(req.getParameter("bookId"));

        Map<String, String> params = new HashMap<>();
        params.compute("name", (k, v) -> req.getParameter("name"));
        params.compute("author", (k, v) -> req.getParameter("author"));
        params.compute("description", (k, v) -> req.getParameter("description"));
        params.compute("rackId", (k, v) -> req.getParameter("rackId"));
        params.compute("shelf", (k, v) -> req.getParameter("shelf"));

        try {
            service.patchBook(bookId, params);
        } catch (NumberFormatException e) {
            resp.sendError(400, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String name = req.getParameter("name");
            String author = req.getParameter("author");
            String description = req.getParameter("description");
            String rackId = req.getParameter("rackId");
            String shelf = req.getParameter("shelf");

            if(name == null || author == null || description == null || rackId == null || shelf == null) {
                throw new IllegalArgumentException("One of the arguments is missing, can't create a book.");
            }

            Book book = new Book(name, author, description, Integer.parseInt(rackId), Integer.parseInt(shelf));
            int bookId = book.hashCode();
            service.putBook(book, bookId);
        } catch (IllegalArgumentException e) {
            resp.sendError(400, e.getMessage());
        }
    }

    @Override
    public void destroy() {
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(decoder.encode(service.getStorage()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}