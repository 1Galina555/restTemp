package Model;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Library;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
public class LibraryTest {
    @Test
    void testConstructorId() {
        Library library = new Library();
        assertNotNull(library.getId());
    }



    @Test
    void testGettersAndSetters() {
        Library library=new Library();
        long id = 80000;
        library.setId(id);
        library.setTitle("LibraryTest");
        library.setAuthors(Arrays.asList(new Author()));
        library.setBooks(Arrays.asList(new Book()));


        assertEquals(id, library.getId());
        assertEquals("LibraryTest", library.getTitle());
        assertEquals(1, library.getAuthors().size());
        assertEquals(1, library.getBooks().size());
    }



    @Test
    void equalsLibraryNames() {
        Library library=new Library();
        library.setTitle("LibraryTest");
        library.setAuthors(Arrays.asList(new Author()));
        library.setBooks(Arrays.asList(new Book()));

        Library library2=new Library();
        library2.setTitle("LibraryNews");
        library2.setAuthors(Arrays.asList(new Author()));
        library2.setBooks(Arrays.asList(new Book()));

        assertFalse(library.equals(library2) || library2.equals(library));
    }


}
