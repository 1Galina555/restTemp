package org.example.repository.impl;
import org.example.db.DataBaseConnect;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Library;
import org.example.repository.SimpleAuthorRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleAuthorRepositoryImpl implements SimpleAuthorRepository {
    public  SimpleAuthorRepositoryImpl(){

    }
    private DataBaseConnect dataBase;
    public SimpleAuthorRepositoryImpl(DataBaseConnect dataBase) {
        this.dataBase = dataBase;
    }
    @Override
    public Author findById(long id) {
        String name = "";
        String lastName = "";
        dataBase.connect();
        try {
            PreparedStatement preparedStatement = dataBase.getPreparedStatement(
                    "SELECT * FROM authors WHERE id=?;"
            );
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    name = rs.getString(2);
                    lastName = rs.getString(3);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.disconnect();
        }
        Author author = new Author();
        author.setId(id);
        author.setName(name);
        author.setLastName(lastName);
        return author;
    }
    @Override
    public List<Author> findAll() {
        dataBase.connect();
        List<Author> authors = new ArrayList<>();
        try (ResultSet rs = dataBase.getStatement().executeQuery("SELECT * FROM authors;")) {
            while (rs.next()) {
                long authorId = rs.getLong(1);
                String name = rs.getString(2);
                String lastName = rs.getString(3);
                Author author = new Author();
                author.setId(authorId);
                author.setName(name);
                author.setLastName(lastName);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.disconnect();
        }
        return authors;
    }

    @Override
    public boolean deleteByID(long id) {
        dataBase.connect();
        try {
            PreparedStatement preparedStatement = dataBase.getPreparedStatement("DELETE FROM authors WHERE id=?;");
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

    public List<Book> getBooksById(long id) {
        Author author = this.findById(id);
        List<Book> books = new ArrayList<>();
        dataBase.connect();
        try {
            PreparedStatement preparedStatement = dataBase.getPreparedStatement(
                    "SELECT id, title FROM books WHERE author_id=?;"
            );
            preparedStatement.setLong(1, author.getId());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    long bookId = rs.getLong(1);
                    String bookTitle = rs.getString(2);
                    String genreBook = rs.getString(3);
                    Book book = new Book();
                    book.setId(bookId);
                    book.setTitle(bookTitle);
                    book.setGenre(genreBook);
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

    public List<Library> getLibraryById(long id) {
        Author author = this.findById(id);
        List<Library> libraries = new ArrayList<>();
        dataBase.connect();
        try {
            dataBase.getConnection().setAutoCommit(false);
            PreparedStatement selectLibraryId = dataBase.getPreparedStatement(
                    "SELECT library_id FROM libraries_authors WHERE author_id=?;"
            );
            PreparedStatement selectLibraryDetails = dataBase.getPreparedStatement(
                    "SELECT title FROM libraries WHERE id=?"
            );
            selectLibraryId.setLong(1, author.getId());
            try (ResultSet getLibraryIdRs = selectLibraryId.executeQuery()) {
                while (getLibraryIdRs.next()) {
                    long libraryId = getLibraryIdRs.getLong(1);
                    selectLibraryDetails.setLong(1, libraryId);
                    try (ResultSet getLibraryDetailsRs = selectLibraryDetails.executeQuery()) {
                        while (getLibraryDetailsRs.next()) {
                            String title = getLibraryDetailsRs.getString(1);
                            Library library = new Library();
                            library.setId(libraryId);
                            library.setTitle(title);
                            libraries.add(library);
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
        return libraries;

    }
    @Override
    public Author save(Author author) {
        dataBase.connect();
        try {
            PreparedStatement preparedStatement = dataBase.getPreparedStatement(
                    "INSERT INTO authors (name, lastname) VALUES (?, ?);"
            );
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.executeUpdate();
            return author;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.disconnect();
        }
        String name="";
        String lastName="";
        Author author1=new Author(name,lastName);
        return author1;
    }

    public boolean updateById(long id, String newName, String newLastName) {
        Author author = this.findById(id);
        dataBase.connect();
        try {
            PreparedStatement preparedStatement = dataBase.getPreparedStatement(
                    "UPDATE authors SET name = ?, lastname = ? WHERE id = ?;"
            );
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newLastName);
            preparedStatement.setLong(3, author.getId());
            preparedStatement.executeUpdate();
            author.setName(newName);
            author.setLastName(newLastName);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.disconnect();
        }
        return false;
    }

    public Author getByName(String name, String lastName) {
        long id = 0;
        dataBase.connect();
        try {
            PreparedStatement preparedStatement = dataBase.getPreparedStatement(
                    "SELECT * FROM authors WHERE (name, lastname)=(?, ?);"
            );
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    id = rs.getLong(1);
                    name = rs.getString(2);
                    lastName = rs.getString(3);
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
            Author author = new Author();
            author.setId(id);
            author.setName(name);
            author.setLastName(lastName);
            author.setBooks(getBooksById(id));
            author.setLibraries(getLibraryById(id));
            return author;
        } else {
            return null;
        }
    }
}




