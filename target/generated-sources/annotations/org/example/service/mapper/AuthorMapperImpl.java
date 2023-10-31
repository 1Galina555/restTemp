package org.example.service.mapper;

import java.util.ArrayList;
import java.util.List;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Library;
import org.example.service.dto.AuthorDto;
import org.example.service.dto.BookDto;
import org.example.service.dto.LibraryDto;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-31T13:25:42+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
*/
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorDto toDto(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorDto authorDto = new AuthorDto();

        authorDto.setId( author.getId() );
        authorDto.setName( author.getName() );
        authorDto.setLastName( author.getLastName() );
        authorDto.setLibraries( libraryListToLibraryDtoList( author.getLibraries() ) );
        authorDto.setBooks( bookListToBookDtoList( author.getBooks() ) );

        return authorDto;
    }

    @Override
    public Author fromDto(AuthorDto authorDto) {
        if ( authorDto == null ) {
            return null;
        }

        Author author = new Author();

        author.setId( authorDto.getId() );
        author.setName( authorDto.getName() );
        author.setLastName( authorDto.getLastName() );
        author.setBooks( bookDtoListToBookList( authorDto.getBooks() ) );
        author.setLibraries( libraryDtoListToLibraryList( authorDto.getLibraries() ) );

        return author;
    }

    protected BookDto bookToBookDto(Book book) {
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

    protected List<BookDto> bookListToBookDtoList(List<Book> list) {
        if ( list == null ) {
            return null;
        }

        List<BookDto> list1 = new ArrayList<BookDto>( list.size() );
        for ( Book book : list ) {
            list1.add( bookToBookDto( book ) );
        }

        return list1;
    }

    protected List<AuthorDto> authorListToAuthorDtoList(List<Author> list) {
        if ( list == null ) {
            return null;
        }

        List<AuthorDto> list1 = new ArrayList<AuthorDto>( list.size() );
        for ( Author author : list ) {
            list1.add( toDto( author ) );
        }

        return list1;
    }

    protected LibraryDto libraryToLibraryDto(Library library) {
        if ( library == null ) {
            return null;
        }

        LibraryDto libraryDto = new LibraryDto();

        libraryDto.setId( library.getId() );
        libraryDto.setTitle( library.getTitle() );
        libraryDto.setBooks( bookListToBookDtoList( library.getBooks() ) );
        libraryDto.setAuthors( authorListToAuthorDtoList( library.getAuthors() ) );

        return libraryDto;
    }

    protected List<LibraryDto> libraryListToLibraryDtoList(List<Library> list) {
        if ( list == null ) {
            return null;
        }

        List<LibraryDto> list1 = new ArrayList<LibraryDto>( list.size() );
        for ( Library library : list ) {
            list1.add( libraryToLibraryDto( library ) );
        }

        return list1;
    }

    protected Book bookDtoToBook(BookDto bookDto) {
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

    protected List<Book> bookDtoListToBookList(List<BookDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Book> list1 = new ArrayList<Book>( list.size() );
        for ( BookDto bookDto : list ) {
            list1.add( bookDtoToBook( bookDto ) );
        }

        return list1;
    }

    protected List<Author> authorDtoListToAuthorList(List<AuthorDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Author> list1 = new ArrayList<Author>( list.size() );
        for ( AuthorDto authorDto : list ) {
            list1.add( fromDto( authorDto ) );
        }

        return list1;
    }

    protected Library libraryDtoToLibrary(LibraryDto libraryDto) {
        if ( libraryDto == null ) {
            return null;
        }

        Library library = new Library();

        library.setId( libraryDto.getId() );
        library.setAuthors( authorDtoListToAuthorList( libraryDto.getAuthors() ) );
        library.setTitle( libraryDto.getTitle() );
        library.setBooks( bookDtoListToBookList( libraryDto.getBooks() ) );

        return library;
    }

    protected List<Library> libraryDtoListToLibraryList(List<LibraryDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Library> list1 = new ArrayList<Library>( list.size() );
        for ( LibraryDto libraryDto : list ) {
            list1.add( libraryDtoToLibrary( libraryDto ) );
        }

        return list1;
    }
}
