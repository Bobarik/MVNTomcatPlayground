package com.vlaskorobogatov.model.libstorage;

import com.vlaskorobogatov.controller.exceptions.LibrarySQLException;
import com.vlaskorobogatov.model.connection.LibraryDao;
import com.vlaskorobogatov.controller.exceptions.BookNotFoundException;
import com.vlaskorobogatov.controller.exceptions.IncorrectParameterException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LibraryServicePostgre implements LibraryService {

    LibraryDao dao;

    public LibraryServicePostgre() throws IOException {
        dao = new LibraryDao();
    }

    @Override
    public Book getBookById(int bookId) {
        try {
            PreparedStatement statement = dao.getStatement("SELECT * FROM book WHERE id = ?");
            statement.setInt(1, bookId);
            ResultSet result = dao.getResult(statement);

            if (!result.next()) {
                return null;
            }

            Book book = new Book();
            book.setTitle(result.getString("title"));
            book.setAuthor(result.getString("author"));
            book.setDescription(result.getString("description"));
            book.setRackId(result.getInt("rack_id"));
            book.setShelf(result.getInt("shelf"));
            return book;
        } catch (SQLException e) {
            throw new LibrarySQLException(e.getMessage());
        }
    }

    @Override
    public void postBook(Book book) {
        try {
            String query;
            PreparedStatement statement;
            query = "INSERT INTO book (title, rack_id, shelf, description, author) " + "VALUES(?, ?, ?, ?, ?)";
            statement = dao.getStatement(query);

            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getRackId());
            statement.setInt(3, book.getShelfNumber());
            statement.setString(4, book.getDescription());
            statement.setString(5, book.getAuthor());
            dao.doUpdate(statement);
        } catch (SQLException e) {
            throw new LibrarySQLException(e.getMessage());
        }
    }

    @Override
    public void deleteBook(int bookId) {
        try {
            PreparedStatement statement = dao.getStatement("DELETE FROM book WHERE id = ?");
            statement.setInt(1, bookId);
            if (dao.doUpdate(statement) == 0)
                throw new BookNotFoundException("Book with this ID doesn't exist");
        } catch (SQLException e) {
            throw new LibrarySQLException(e.getMessage());
        }
    }

    @Override
    public void patchBook(int id, Map<String, String> properties) {
        try {
            Book book = getBookById(id);
            String query = "UPDATE book SET title = ?, author = ?, description = ?, shelf = ?, rack_id = ? WHERE id = ?";
            PreparedStatement statement = dao.getStatement(query);

            if (properties.get("title") != null) {
                statement.setString(1, properties.get("title"));
            } else {
                statement.setString(1, book.getTitle());
            }

            if (properties.get("author") != null) {
                statement.setString(2, properties.get("author"));
            } else {
                statement.setString(2, book.getTitle());
            }

            if (properties.get("description") != null) {
                statement.setString(3, properties.get("description"));
            } else {
                statement.setString(3, book.getTitle());
            }

            if (properties.get("shelf") != null) {
                statement.setInt(4, Integer.parseInt(properties.get("shelf")));
            } else {
                statement.setInt(4, book.getShelfNumber());
            }

            if (properties.get("rackId") != null) {
                statement.setInt(5, Integer.parseInt(properties.get("rackId")));
            } else {
                statement.setInt(5, book.getRackId());
            }

            statement.setInt(6, id);

            dao.doUpdate(statement);
        } catch (SQLException e) {
            throw new LibrarySQLException(e.getMessage());
        } catch (NumberFormatException e) {
            throw new IncorrectParameterException("Incorrect parameter: shelf, rackId should be integers.");
        }
    }

    @Override
    public Map<Integer, Book> getBooks(Integer rackId, Integer shelfNumber, String bookTitle) {
        try {
            String queryString = "SELECT * FROM book WHERE TRUE";
            PreparedStatement statement;

            if (rackId != null) {
                queryString += " AND rack_id = " + rackId;
            }
            if (shelfNumber != null) {
                queryString += " AND shelf = " + shelfNumber;
            }
            if (bookTitle != null) {
                queryString += " AND title = ?";
                statement = dao.getStatement(queryString);
                statement.setString(1, bookTitle);
            } else {
                statement = dao.getStatement(queryString);
            }

            ResultSet result = dao.getResult(statement);

            Map<Integer, Book> books = new HashMap<>();
            while (result.next()) {
                Book book = new Book();
                book.setTitle(result.getString("title"));
                book.setAuthor(result.getString("author"));
                book.setDescription(result.getString("description"));
                book.setRackId(result.getInt("rack_id"));
                book.setShelf(result.getInt("shelf"));
                books.put(result.getInt("id"), book);
            }
            return books;
        } catch (SQLException e) {
            throw new LibrarySQLException(e.getMessage());
        }
    }
}
