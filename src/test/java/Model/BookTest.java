package Model;

import org.example.model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class BookTest {
    @Test
    void testConstructor() {
        Book book = new Book();
        assertNotNull(book.getId());
    }

    @Test
    void testGettersAndSetters() {
        Book book = new Book();
        long id = 5555;
        book.setId(id);
        book.setTitle("Test book");
        book.setGenre("Horror");

        assertEquals(id, book.getId());
        assertEquals("Test book", book.getTitle());
        assertEquals("Horror", book.getGenre());
    }


}
