import org.example.db.DataBaseConnect;
import org.example.model.Book;
import org.example.repository.impl.SimpleBookRepositoryImpl;
import org.example.service.ServiceBook;
import org.example.service.dto.BookDto;
import org.example.service.implService.BookServiceImpl;
import org.example.service.mapper.BookMapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private DataBaseConnect dataBase;

    @Mock
    private SimpleBookRepositoryImpl bookRepository;

    private BookServiceImpl bookService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        bookService = new BookServiceImpl(dataBase);
        bookService.setBookRepository(bookRepository);
    }

    @Test
    public void save() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Master and Margarita");
        bookDto.setGenre("roman");
        bookDto.setLibraryId(1);
        bookDto.setAuthorId(1);
        BookDto result = bookService.save(bookDto);
        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getGenre(), result.getGenre());
        assertEquals(bookDto.getLibraryId(), result.getLibraryId());
        assertEquals(bookDto.getAuthorId(), result.getAuthorId());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void findById() {
        long id = 1;
        Book book = new Book();
        book.setId(id);
        when(bookRepository.findById(id)).thenReturn(book);
        BookDto result = bookService.findById(id);
        assertEquals(id, result.getId());
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    public void deleteById() {
        long id = 1;
        when(bookRepository.deleteById(id)).thenReturn(true);
        boolean result = bookService.deleteById(id);
        assertEquals(true, result);
        verify(bookRepository, times(1)).deleteById(id);
    }
}