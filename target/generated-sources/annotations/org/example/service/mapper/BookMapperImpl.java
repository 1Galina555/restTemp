package org.example.service.mapper;

import org.example.model.Book;
import org.example.service.dto.BookDto;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-31T13:25:42+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
*/
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDto toDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDto bookDto = new BookDto();

        bookDto.setId( book.getId() );
        bookDto.setTitle( book.getTitle() );
        bookDto.setGenre( book.getGenre() );
        bookDto.setLibraryId( book.getLibraryId() );
        bookDto.setAuthorId( book.getAuthorId() );

        return bookDto;
    }

    @Override
    public Book fromDto(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        Book book = new Book();

        book.setId( bookDto.getId() );
        book.setTitle( bookDto.getTitle() );
        book.setGenre( bookDto.getGenre() );
        book.setLibraryId( bookDto.getLibraryId() );
        book.setAuthorId( bookDto.getAuthorId() );

        return book;
    }
}
