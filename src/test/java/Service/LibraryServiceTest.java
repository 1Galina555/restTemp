package Service;
import org.example.db.DataBaseConnect;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Library;
import org.example.repository.impl.SimpleLibraryRepositoryImpl;
import org.example.service.dto.AuthorDto;
import org.example.service.dto.BookDto;
import org.example.service.dto.LibraryDto;
import org.example.service.implService.LibraryServiceImpl;
import org.example.service.mapper.LibraryMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
public class LibraryServiceTest {
    @Mock
    private DataBaseConnect dataBase;

    @Mock
    private SimpleLibraryRepositoryImpl libraryRepository;

    private LibraryServiceImpl libraryService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        libraryService = new LibraryServiceImpl(dataBase);
        libraryService.setLibraryRepository(libraryRepository);
    }

    @Test
    public void save() {
        LibraryDto libraryDto = new LibraryDto();
        libraryDto.setTitle("Library London");
        Library library = LibraryMapper.INSTANCE.fromDto(libraryDto);
        when(libraryRepository.save(library)).thenReturn(library);
        when(libraryRepository.findById(library.getId())).thenReturn(library);
        LibraryDto result = libraryService.save(libraryDto);
        assertEquals(libraryDto.getTitle(), result.getTitle());
    }

    @Test
    public void findById() {
        long id = 1;
        Library library = new Library();
        library.setId(id);
        when(libraryRepository.findById(id)).thenReturn(library);
        LibraryDto result = libraryService.findById(id);
        assertEquals(id, result.getId());
        verify(libraryRepository, times(1)).findById(id);
    }

    @Test
    public void testUpdateById() {
        long id = 1L;
        LibraryDto libraryDto = new LibraryDto();
        libraryDto.setTitle("Red Moscow");
        Library library = new Library();
        library.setId(id);
        library.setTitle("Kremlin");
        when(libraryRepository.findById(id)).thenReturn(library);
        LibraryDto updatedLibraryDto = libraryService.updateById(id, libraryDto);
        assertEquals(libraryDto.getTitle(), updatedLibraryDto.getTitle());
    }

    @Test
    public void testDeleteById() {
        long id = 1L;
        when(libraryRepository.deleteById(id)).thenReturn(true);
        boolean result = libraryService.deleteById(id);
        assertTrue(result);
        verify(libraryRepository, times(1)).deleteById(id);
    }
    @Test
    public void testGetAll() {
        List<Library> libraries = new ArrayList<>();
        libraries.add(new Library("Library 1"));
        libraries.add(new Library("Library 2"));
        when(libraryRepository.findAll()).thenReturn(libraries);
        List<LibraryDto> libraryDtoList = libraryService.getAll();
        assertEquals(libraries.size(), libraryDtoList.size());
        verify(libraryRepository, times(1)).findAll();
    }

    @Test
    public void testGetBooksById() {
        long id = 1L;
        List<Book> books = new ArrayList<>();
        books.add(new Book("Book1","classic",1,2));
        books.add(new Book("Book1","classic",1,2));
        when(libraryRepository.getBookById(id)).thenReturn(books);
        List<BookDto> bookDtoList = libraryService.getBooksById(id);
        assertEquals(books.size(), bookDtoList.size());
        verify(libraryRepository, times(1)).getBookById(id);
    }

    @Test
    public void testGetAuthorsById() {
        long id = 1L;
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Marina", "Tsvetaeva"));
        authors.add(new Author("Nikolai", "Gogol"));
        when(libraryRepository.getAuthorsById(id)).thenReturn(authors);
        List<AuthorDto> authorDtoList = libraryService.getAuthorsById(id);
        assertEquals(authors.size(), authorDtoList.size());
        verify(libraryRepository, times(1)).getAuthorsById(id);
    }

    @Test
    public void testGetByTitle() {
        String title = "Kremlin";
        Library library = new Library(title);
        when(libraryRepository.getByTitle(title)).thenReturn(library);
        LibraryDto libraryDto = libraryService.getByTitle(title);
        assertEquals(title, libraryDto.getTitle());
        verify(libraryRepository, times(1)).getByTitle(title);
    }

}
