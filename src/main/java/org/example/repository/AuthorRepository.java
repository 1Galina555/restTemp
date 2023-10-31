package org.example.repository;
import org.example.model.Author;
import org.example.model.Book;

import java.util.List;

public interface AuthorRepository <T, K> {
    T findById(long id);

    List<Author> findAll();
    boolean deleteByID(long id);
    List<Book>getBooksById(long id);

    T save(T t);
}
