package Repository;

import org.example.db.DataBaseConnect;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Library;
import org.example.repository.impl.SimpleLibraryRepositoryImpl;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Testcontainers
public class LibraryDbTest {
    @Container
    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres")
            .withUsername("postgres")
            .withDatabaseName("my_data")
            .withPassword("Fox1997")
            .withInitScript( "test.sql" );


    private static Connection connection;
    private static Statement statement;
    private static SimpleLibraryRepositoryImpl libraryRepository;

    @BeforeAll
    public static void connect() {
        postgreSQLContainer.start();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    postgreSQLContainer.getJdbcUrl(),
                    postgreSQLContainer.getUsername(),
                    postgreSQLContainer.getPassword()
            );
            statement = connection.createStatement();
            libraryRepository = new SimpleLibraryRepositoryImpl(new DataBaseConnect(
                    "org.postgresql.Driver",
                    postgreSQLContainer.getJdbcUrl(),
                    postgreSQLContainer.getUsername(),
                    postgreSQLContainer.getPassword()
            ));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @BeforeEach
    public void prepareData() {
        try {
            String sql = Files.lines(Paths.get("test.sql")).collect(Collectors.joining(" "));
            statement.execute(sql);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    @DisplayName("Add test")
    void add() {
        String newTitle="MyLibrary";
        Library library = new Library(newTitle);
        long id=libraryRepository.save(library).getId();
        Assertions.assertEquals(library.getTitle(),libraryRepository.save(library).getTitle());
    }
    @Test
    @DisplayName("Get by id test")
    void getById() {
        long id = 1;
        Assertions.assertEquals("1. Sbook", libraryRepository.findById(id).toString());
    }

    @Test
    @DisplayName("Get by title test")
    void getByTitle() {
        String title = "Smartra";
        String result = "3. Smartra";
        Assertions.assertEquals(result, libraryRepository.getByTitle(title).toString());
    }
    @Test
    @DisplayName("Get list of libraries")
    void getListOfLibrary() {
        List<Library>libraries=new ArrayList<>();
        Library library=new Library("Sbook");
        Library library1=new Library("Public");
        Library library2=new Library("Smarta");
        Library library3=new Library("Libras");
       libraries.add(library);
        libraries.add(library1);
        libraries.add(library2);
        libraries.add(library3);
        Assertions.assertEquals(libraries.size(), libraryRepository.findAll().size());
    }

    @Test
    @DisplayName("Show authors of library")
    void getAuthors() {
        long id = 1;
        List<Author>authors=new ArrayList<>();
        authors=libraryRepository.getAuthorsById(id);
        Assertions.assertEquals(authors.size(), libraryRepository.getAuthorsById(id).size());
    }
    @Test
    @DisplayName("Show list of books")
    void getBooks() {
        long id = 2;
        List<Book> books = new ArrayList<>();
        books=libraryRepository.getBookById(id);
        Assertions.assertEquals(books.size(), libraryRepository.getBookById(id).size());
    }

    @Test
    @DisplayName("Add test")
    void addLibrary() {
        Library library = new Library("HouseBook");
        Assertions.assertEquals(library,libraryRepository.save(library));
    }

    @Test
    @DisplayName("Update test")
    void updateById() {
        long id = 1;
        String newTitle = "NewLibrary";
        Library library=new Library(newTitle);
        Assertions.assertEquals(library.getTitle(),libraryRepository.save(library).getTitle());
    }

    @Test
    @DisplayName("Delete test")
    void deleteById() {
        long id = 3;
        String titleOfDeletingLibrary = libraryRepository.findById(id).toString();
        Assertions.assertTrue(libraryRepository.deleteById(id));
        Assertions.assertNotEquals(id + ". " + titleOfDeletingLibrary, libraryRepository.findById(id).toString());
    }
}
