package org.example.service.mapper;
import org.example.model.Author;
import org.example.model.Book;
import org.example.service.dto.BookDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE= Mappers.getMapper(BookMapper.class);
    BookDto toDto(Book book);
    Book fromDto(BookDto bookDto);
    default Book map(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setTitle( resultSet.getString( 2 ) );
        book.setGenre( resultSet.getString( 3 ));
        return book;
    }
}
