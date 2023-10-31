package org.example.repository.impl;
import org.example.db.DataBaseConnect;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Library;
import org.example.repository.SimpleLibraryRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleLibraryRepositoryImpl implements SimpleLibraryRepository {
    private final DataBaseConnect dataBase;

    public SimpleLibraryRepositoryImpl(DataBaseConnect dataBase) {
        this.dataBase = dataBase;
    }
    @Override
    public Library findById(Long id) {
        String title = "";
        dataBase.connect();
        try {
            PreparedStatement preparedStatement = dataBase.getPreparedStatement("SELECT * FROM libraries WHERE id=?;");
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    id = rs.getLong(1);
                    title = rs.getString(2);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.disconnect();
        }
        Library library = new Library();
        library.setId(id);
        library.setTitle(title);
        return library;
    }

    @Override
    public boolean deleteById(long id) {
        dataBase.connect();
        try {
            PreparedStatement preparedStatement = dataBase.getPreparedStatement("DELETE FROM libraries WHERE id=?;");
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
    public List<Library> findAll() {
        List<Library> libraries = new ArrayList<>();
        dataBase.connect();
        try (ResultSet rs = dataBase.getStatement().executeQuery("SELECT * FROM libraries;")) {
            while (rs.next()) {
                long libraryId = rs.getLong(1);
                String title= rs.getString(2);
                Library library= new Library();
                library.setId(libraryId);
                library.setTitle(title);
                libraries.add(library);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.disconnect();
        }
        return libraries;
    }

    public Library save(Library library) {
        dataBase.connect();
        String sql = "INSERT INTO libraries (title) VALUES (?);";
        try {
            PreparedStatement preparedStatement = dataBase.getPreparedStatement(sql);
            preparedStatement.setString(1, library.getTitle());
            preparedStatement.executeUpdate();
            return library;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.disconnect();
        }
        Library library1=new Library();
        return library1;
    }
    public List<Book> getBookById(long id) {
        Library library = this.findById(id);
        List<Book> books = new ArrayList<>();
        dataBase.connect();
        try {
            PreparedStatement preparedStatement = dataBase.getPreparedStatement(
                    "SELECT id, title, genre FROM books WHERE library_id=?;"
            );
            preparedStatement.setLong(1, library.getId());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    long bookId = rs.getLong(1);
                    String bookTitle = rs.getString(2);
                    String bookGenre = rs.getString(3);
                    Book book = new Book();
                    book.setId(bookId);
                    book.setTitle(bookTitle);
                    book.setGenre(bookGenre);
                    books.add(book);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.disconnect();
        }
        return books;
    }

    public List<Author> getAuthorsById(long id) {
        Library library = findById(id);
        List<Author> authors = new ArrayList<>();
        dataBase.connect();
        try {
            dataBase.getConnection().setAutoCommit(false);
            PreparedStatement selectAuthorsId = dataBase.getPreparedStatement(
                    "SELECT author_id FROM libraries_authors WHERE library_id=?;"
            );
            PreparedStatement selectAuthorsDetails = dataBase.getPreparedStatement(
                    "SELECT name, lastname FROM authors WHERE id=?"
            );
            selectAuthorsId.setLong(1, library.getId());
            try (ResultSet getAuthorIdRs = selectAuthorsId.executeQuery()) {
                while (getAuthorIdRs.next()) {
                    long authorId = getAuthorIdRs.getLong(1);
                    selectAuthorsDetails.setLong(1, authorId);
                    try (ResultSet getAuthorDetailsRs = selectAuthorsDetails.executeQuery()) {
                        while (getAuthorDetailsRs.next()) {
                            String authorName = getAuthorDetailsRs.getString(1);
                            String authorLastName = getAuthorDetailsRs.getString(2);
                            Author author = new Author();
                            author.setId(authorId);
                            author.setName(authorName);
                            author.setLastName(authorLastName);
                            authors.add(author);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.disconnect();
        }
        return authors;
    }
    public Library getByTitle(String title) {
        long id = 0;
        dataBase.connect();
        try {
            PreparedStatement preparedStatement = dataBase.getPreparedStatement("SELECT * FROM libraries WHERE title=?;");
            preparedStatement.setString(1, title);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    id = rs.getLong(1);
                    title = rs.getString(2);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.disconnect();
        }
        if (id > 0) {
            Library library = new Library();
            library.setId(id);
            library.setTitle(title);
            library.setBooks(getBookById(id));
            library.setAuthors(getAuthorsById(id));
            return library;
        } else {
            Library library=new Library();
            return library;
        }
    }
}
