package org.example.repository;
import org.example.model.Book;
import java.util.List;

public interface BookRepository<T, K> {
    T findById(long id);

    boolean deleteById(long id);

    List<Book> findAll();

    T save(T t);
}
