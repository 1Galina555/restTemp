package org.example.service.implService;
import org.example.db.DataBaseConnect;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Library;
import org.example.repository.impl.SimpleAuthorRepositoryImpl;
import org.example.repository.impl.SimpleBookRepositoryImpl;
import org.example.service.mapper.AuthorMapper;
import org.example.service.mapper.BookMapper;
import org.example.service.mapper.LibraryMapper;
import org.example.service.ServiceAuthor;
import org.example.service.dto.AuthorDto;
import org.example.service.dto.BookDto;
import org.example.service.dto.LibraryDto;
import java.util.ArrayList;
import java.util.List;

public class AuthorServiceImpl implements ServiceAuthor {
    private final DataBaseConnect dataBase;
    private SimpleAuthorRepositoryImpl authorRepository;

    public SimpleAuthorRepositoryImpl getAuthorRepository() {
        return authorRepository;
    }

    public AuthorServiceImpl(DataBaseConnect dataBase) {
        this.dataBase = dataBase;
        this.authorRepository = new SimpleAuthorRepositoryImpl(dataBase);

    }
    @Override
    public AuthorDto findById(long id) {
        Author author=authorRepository.findById(id);
        AuthorDto authorDto=new AuthorDto();
        authorDto=AuthorMapper.INSTANCE.toDto(author);
            return authorDto;
    }
    public List<AuthorDto> getAllAuthors() {
        List<Author>authors=authorRepository.findAll();
        List<AuthorDto>authorsDto=new ArrayList<>();
        for(Author author:authors) {
            authorsDto.add(AuthorMapper.INSTANCE.toDto(author));
        }
        return authorsDto;
    }

    public List<LibraryDto> getLibraryById(long id) {
        List<Library>libraries=authorRepository.getLibraryById(id);
        List<LibraryDto>libraryDtoList=new ArrayList<>();
        for (Library library:libraries){
            libraryDtoList.add(LibraryMapper.INSTANCE.toDto(library));
        }
        return libraryDtoList;
    }
    public List<BookDto> getBooksById(long id) {
        List<Book>books=authorRepository.getBooksById(id);
        List<BookDto>bookDtos=new ArrayList<>();
        for (Book book:books){
            bookDtos.add(BookMapper.INSTANCE.toDto(book));
        }
        return bookDtos;
    }
    public AuthorDto addAuthor(AuthorDto authorDto) {
        Author author = new Author(authorDto.getName(),authorDto.getLastName());
        authorDto=AuthorMapper.INSTANCE.toDto(author);
        authorRepository.save(author);
        return authorDto;
    }
    public boolean deleteById(long id) {
        return authorRepository.deleteByID(id);
    }
    public boolean updateById(long id,AuthorDto authorDto) {
        return authorRepository.updateById(id, authorDto.getName(),authorDto.getLastName());
    }
    public AuthorDto getByFullName(String name, String lastName) {
        Author author=authorRepository.getByName(name, lastName);
        AuthorDto authorDto=new AuthorDto();
        authorDto=AuthorMapper.INSTANCE.toDto(author);
        return authorDto;
    }
    public void setAuthorRepository(SimpleAuthorRepositoryImpl authorRepository) {
        this.authorRepository = authorRepository;
    }



}
