package org.example.repository.impl;
import org.example.db.DataBaseConnect;
import org.example.model.Book;
import org.example.repository.SimpleBookRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleBookRepositoryImpl implements SimpleBookRepository {
    private DataBaseConnect dataBase;
    public SimpleBookRepositoryImpl(DataBaseConnect dataBase) {
        this.dataBase = dataBase;
    }
    @Override
    public Book findById(long id) {
        Book book = new Book();
        dataBase.connect();
        try {
            PreparedStatement preparedStatement = dataBase.getPreparedStatement(
                    "SELECT title, genre, library_id, author_id FROM books WHERE id=?"
            );
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    book.setId(id);
                    book.setTitle(rs.getString(1));
                    book.setGenre(rs.getString(2));
                    book.setLibraryId(rs.getLong(3));
                    book.setAuthorId(rs.getLong(4));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
    @Override
    public boolean deleteById(long id) {
        dataBase.connect();
        try {
            PreparedStatement preparedStatement = dataBase.getPreparedStatement("DELETE FROM books WHERE id=?;");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.disconnect();
        }
        return false;
    }
    @Override
    public List<Book> findAll() {
        dataBase.connect();
        List<Book> books= new ArrayList<>();
        try (ResultSet rs = dataBase.getStatement().executeQuery("SELECT * FROM books;")) {
            while (rs.next()) {
                long bookId = rs.getLong(1);
                String title = rs.getString(2);
                String genre = rs.getString(3);
                long libraryId=rs.getLong(4);
                long authorId=rs.getLong(5);
                Book book = new Book();
                book.setId(bookId);
                book.setTitle(title);
                book.setGenre(genre);
                book.setLibraryId(libraryId);
                book.setAuthorId(authorId);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.disconnect();
        }
        return books;
    }
    @Override
    public Book save(Book book) {
        String libraryIdCheck = "SELECT * FROM libraries WHERE id = ?";
        String authorIdCheck = "SELECT * FROM authors WHERE id = ?";
        String insertBook = "INSERT INTO books (title, genre, library_id, author_id) VALUES (?, ?, ?);";
        String insertLibrariesAuthors = "INSERT INTO libraries_authors (library_id, author_id) VALUES (?, ?);";
        dataBase.connect();
        try {
            dataBase.getConnection().setAutoCommit(false);
            PreparedStatement libraryIdCheckStatement = dataBase.getPreparedStatement(libraryIdCheck);
            libraryIdCheckStatement.setLong(1, book.getLibraryId());
            PreparedStatement authorIdCheckStatement = dataBase.getPreparedStatement(authorIdCheck);
            authorIdCheckStatement.setLong(1, book.getAuthorId());
            PreparedStatement preparedStatement = dataBase.getPreparedStatement(insertBook);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getGenre());
            preparedStatement.setLong(3, book.getLibraryId());
            preparedStatement.setLong(4, book.getAuthorId());
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement2 = dataBase.getPreparedStatement(insertLibrariesAuthors);
            preparedStatement2.setLong(1, book.getLibraryId());
            preparedStatement2.setLong(2, book.getAuthorId());
            preparedStatement2.executeUpdate();
            dataBase.getConnection().commit();
            dataBase.getConnection().setAutoCommit(true);
            return book;
        } catch (SQLException e) {
            try {
                dataBase.getConnection().rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            dataBase.disconnect();
        }
        Book book1=new Book();
        return book1;
    }
}
