package com.vlaskorobogatov.controller;

import com.vlaskorobogatov.controller.exceptions.*;
import com.vlaskorobogatov.model.decoder.Decoder;
import com.vlaskorobogatov.model.decoder.JSONDecoder;
import com.vlaskorobogatov.model.decoder.JacksonParser;
import com.vlaskorobogatov.model.libstorage.Book;
import com.vlaskorobogatov.model.libstorage.LibraryService;
import com.vlaskorobogatov.model.libstorage.Storage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/books/*")
public class LibraryServlet extends HttpServlet {
    LibraryService service;
    Decoder decoder;
    String path;

    @Override
    public void init() {
        System.out.println("Initializing servlet");
        path = getServletContext().getRealPath("/") + "WEB-INF/booksonly.json";
        decoder = new JSONDecoder(new JacksonParser(), path);
        service = new LibraryService((Storage) decoder.decode());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            checkUri(req.getRequestURI());
            if (req.getMethod().equalsIgnoreCase("PATCH")) {
                doPatch(req, resp);
            } else {
                super.service(req, resp);
            }
        } catch (InvalidUriException e) {
            handleException(new JsonResponse(400, e), resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String uri = req.getRequestURI();
            int bookId = Integer.parseInt(uri.substring(uri.lastIndexOf("/") + 1));
            service.deleteBook(bookId);
            resp.setStatus(204);
        } catch (BookNotFoundException e) {
            handleException(new JsonResponse(404, e), resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String uri = req.getRequestURI();
        if (uri.matches("/library-servlet/books/\\d+")) {
            getBook(uri, resp);
        } else {
            String rackId = req.getParameter("rackId");
            String shelfNumber = req.getParameter("shelf");
            String bookName = req.getParameter("name");

            try {
                Integer intRackId = (rackId != null ? Integer.parseInt(rackId) : null);
                Integer intShelfNumber = (shelfNumber != null ? Integer.parseInt(shelfNumber) : null);

                Map<Integer, Book> books = service.getBooks(intRackId, intShelfNumber, bookName);

                if (books.isEmpty()) {
                    resp.setStatus(204);
                } else {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(decoder.encode(books));
                }
            } catch (NumberFormatException e) {
                handleException(new JsonResponse(422, e), resp);
            }
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String uri = req.getRequestURI();
            int bookId = Integer.parseInt(uri.substring(uri.lastIndexOf("/") + 1));

            Map<String, String> params = new HashMap<>();
            params.compute("name", (k, v) -> req.getParameter("name"));
            params.compute("author", (k, v) -> req.getParameter("author"));
            params.compute("description", (k, v) -> req.getParameter("description"));
            params.compute("rackId", (k, v) -> req.getParameter("rackId"));
            params.compute("shelf", (k, v) -> req.getParameter("shelf"));

            service.patchBook(bookId, params);
        } catch (IncorrectParameterException | NumberFormatException e) {
            handleException(new JsonResponse(422, e), resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String uri = req.getRequestURI();
            int bookId = Integer.parseInt(uri.substring(uri.lastIndexOf("/") + 1));

            if (service.getBookById(bookId) != null) {
                throw new BookAlreadyExistsException("Book with this ID already exists. If you want to update info on this book use PATCH.");
            } else {
                String name = req.getParameter("name");
                String author = req.getParameter("author");
                String description = req.getParameter("description");
                String rackId = req.getParameter("rackId");
                String shelf = req.getParameter("shelf");

                if (name == null || author == null || description == null || rackId == null || shelf == null) {
                    throw new IncorrectParameterException("One of the arguments is missing, can't create a book.");
                } else {
                    Book book = new Book(name, author, description, Integer.parseInt(rackId), Integer.parseInt(shelf));
                    service.putBook(book, bookId);
                    resp.setStatus(201);
                }
            }
        } catch (IncorrectParameterException | NumberFormatException e) {
            handleException(new JsonResponse(422, e), resp);
        } catch (BookAlreadyExistsException e) {
            handleException(new JsonResponse(409, e), resp);
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

    private void handleException(JsonResponse e, HttpServletResponse resp) throws IOException {
        resp.setStatus(e.getErrorCode());
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(decoder.encode(e));
    }

    private void getBook(String uri, HttpServletResponse resp) throws IOException {
        int bookId = Integer.parseInt(uri.substring(uri.lastIndexOf("/") + 1));
        Book book = service.getBookById(bookId);
        if (book == null) {
            handleException(new JsonResponse(404, new BookNotFoundException("Book with this ID doesn't exist")), resp);
        } else {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(decoder.encode(book));
        }
    }

    private void checkUri(String uri) throws InvalidUriException {
        if (!uri.matches("/library-servlet/books(/\\d+)?"))
            throw new InvalidUriException("Invalid URI, can't process.");
    }
}