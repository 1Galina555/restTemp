package org.example.repository;
import org.example.model.Library;
import java.util.List;

public interface LibraryRepository<T, K> {
    T findById(K id);

    boolean deleteById(long id);

    List<Library> findAll();

}