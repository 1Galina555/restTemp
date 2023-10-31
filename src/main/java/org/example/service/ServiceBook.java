package org.example.service;
import org.example.service.dto.BookDto;

public interface ServiceBook {

    BookDto save(BookDto bookDto);

    BookDto findById(long id);
}
