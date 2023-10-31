import org.example.model.Author;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorTest {
    @Test
    void testConstructor() {
        Author author = new Author();
        assertNotNull(author.getId());
    }

    @Test
    void testGettersAndSetters() {
        Author author = new Author();
        long id = 5555;
        author.setId(id);
        author.setName("Vasilii");
        author.setLastName("Horror");

        assertEquals(id, author.getId());
        assertEquals("Vasilii", author.getName());
        assertEquals("Horror", author.getLastName());
    }
}
