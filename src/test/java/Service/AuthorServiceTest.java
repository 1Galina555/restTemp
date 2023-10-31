import org.example.db.DataBaseConnect;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Library;
import org.example.repository.impl.SimpleAuthorRepositoryImpl;
import org.example.service.implService.AuthorServiceImpl;
import org.example.service.mapper.AuthorMapper;
import org.example.service.mapper.BookMapper;
import org.example.service.mapper.LibraryMapper;
import org.example.service.dto.AuthorDto;
import org.example.service.dto.BookDto;
import org.example.service.dto.LibraryDto;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class AuthorServiceTest {

    @Mock
    private DataBaseConnect dataBase;

    @Mock
    private SimpleAuthorRepositoryImpl authorRepository;

    private AuthorServiceImpl authorService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        authorService = new AuthorServiceImpl(dataBase);
        authorService.setAuthorRepository(authorRepository);
    }

    @Test
    public void findById() {
        long id = 1;
        Author author = new Author();
        author.setId(id);
        when(authorRepository.findById(id)).thenReturn(author);
        AuthorDto result = authorService.findById(id);
        assertEquals(id, result.getId());
        verify(authorRepository, times(1)).findById(id);
    }

    @Test
    public void getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        Author author1 = new Author();
        Author author2 = new Author();
        authors.add(author1);
        authors.add(author2);
        when(authorRepository.findAll()).thenReturn(authors);
        List<AuthorDto> result = authorService.getAllAuthors();
        assertEquals(2, result.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    public void getLibraryById() {
        long id = 1;
        List<Library> libraries = new ArrayList<>();
        Library library1 = new Library();
        Library library2 = new Library();
        libraries.add(library1);
        libraries.add(library2);
        when(authorRepository.getLibraryById(id)).thenReturn(libraries);
        List<LibraryDto> result = authorService.getLibraryById(id);
        assertEquals(2, result.size());
        verify(authorRepository, times(1)).getLibraryById(id);
    }

    @Test
    public void getBooksById() {
        long id = 1;
        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        Book book2 = new Book();
        books.add(book1);
        books.add(book2);
        when(authorRepository.getBooksById(id)).thenReturn(books);
        List<BookDto> result = authorService.getBooksById(id);
        assertEquals(2, result.size());
        verify(authorRepository, times(1)).getBooksById(id);
    }
    @Test
    public void addAuthor() {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName("John");
        authorDto.setLastName("Doe");
        Author author = AuthorMapper.INSTANCE.fromDto(authorDto);
        when(authorRepository.save(author)).thenReturn(author);
        AuthorDto result = authorService.addAuthor(authorDto);
        assertEquals(authorDto.getName(), result.getName());
        assertEquals(authorDto.getLastName(), result.getLastName());
    }

    @Test
    public void deleteById() {
        long id = 1;
        when(authorRepository.deleteByID(id)).thenReturn(true);
        boolean result = authorService.deleteById(id);
        assertEquals(true, result);
    }

    @Test
    public void updateById() {
        long id = 1;
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName("John");
        authorDto.setLastName("Doe");
        when(authorRepository.updateById(id, authorDto.getName(), authorDto.getLastName())).thenReturn(true);
        boolean result = authorService.updateById(id, authorDto);
        assertEquals(true, result);
    }

    @Test
    public void getByFullName() {
        // Arrange
        String name = "John";
        String lastName = "Doe";
        Author author = new Author(name, lastName);
        AuthorDto expectedDto = AuthorMapper.INSTANCE.toDto(author);
        when(authorRepository.getByName(name, lastName)).thenReturn(author);
        AuthorDto result = authorService.getByFullName(name, lastName);
        assertEquals(expectedDto.getName(), result.getName());
        assertEquals(expectedDto.getLastName(), result.getLastName());
    }
}