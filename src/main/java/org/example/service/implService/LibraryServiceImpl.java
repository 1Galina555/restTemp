package org.example.service.implService;

import org.example.db.DataBaseConnect;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Library;
import org.example.repository.impl.SimpleLibraryRepositoryImpl;
import org.example.service.ServiceLibrary;
import org.example.service.dto.AuthorDto;
import org.example.service.dto.BookDto;
import org.example.service.dto.LibraryDto;
import org.example.service.mapper.AuthorMapper;
import org.example.service.mapper.BookMapper;
import org.example.service.mapper.LibraryMapper;

import java.util.ArrayList;
import java.util.List;

public class LibraryServiceImpl implements ServiceLibrary {
    private final DataBaseConnect dataBase;
    private SimpleLibraryRepositoryImpl libraryRepository;

    public LibraryServiceImpl(DataBaseConnect dataBase) {
        this.dataBase = dataBase;
        this.libraryRepository = new SimpleLibraryRepositoryImpl(dataBase);

    }
    @Override
    public LibraryDto save(LibraryDto libraryDto) {
        Library library = new Library(libraryDto.getTitle());
        libraryDto= LibraryMapper.INSTANCE.toDto(library);
        libraryRepository.save(library);
        return libraryDto;
    }
    @Override
    public LibraryDto findById(long id) {
        Library library = libraryRepository.findById(id);
        LibraryDto libraryDto=new LibraryDto();
        libraryDto=LibraryMapper.INSTANCE.toDto(library);
        return libraryDto;
    }
    public LibraryDto updateById(long id, LibraryDto libraryDto) {
        libraryRepository.findById(id).setTitle(libraryDto.getTitle());
        Library library=libraryRepository.findById(id);
        libraryDto=LibraryMapper.INSTANCE.toDto(library);
        return libraryDto;
    }

    public boolean deleteById(long id) {
        return libraryRepository.deleteById(id);
    }
    public List<LibraryDto> getAll() {
        List<Library>libraries=libraryRepository.findAll();
        List<LibraryDto>libraryDtoList=new ArrayList<>();
        for(Library library:libraries) {
            libraryDtoList.add(LibraryMapper.INSTANCE.toDto(library));
        }
        return libraryDtoList;
    }
    public List<BookDto> getBooksById(long id) {
        List<Book>books=libraryRepository.getBookById(id);
        List<BookDto>bookDtos=new ArrayList<>();
        for(Book book:books) {
            bookDtos.add(BookMapper.INSTANCE.toDto(book));
        }
        return bookDtos;
    }
    public List<AuthorDto> getAuthorsById(long id) {
        List<Author>authors=libraryRepository.getAuthorsById(id);
        List<AuthorDto>authorDtos=new ArrayList<>();
        for(Author author:authors) {
            authorDtos.add(AuthorMapper.INSTANCE.toDto(author));
        }
        return authorDtos;
    }
    public LibraryDto getByTitle(String title) {
        Library library = libraryRepository.getByTitle(title);
        LibraryDto libraryDto=new LibraryDto();
        libraryDto=LibraryMapper.INSTANCE.toDto(library);
        return libraryDto;
    }
    public void setLibraryRepository(SimpleLibraryRepositoryImpl libraryRepository) {
        this.libraryRepository = libraryRepository;
    }
}
