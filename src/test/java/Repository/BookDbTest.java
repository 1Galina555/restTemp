package Repository;

import org.example.db.DataBaseConnect;
import org.example.model.Book;
import org.example.repository.impl.SimpleBookRepositoryImpl;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.stream.Collectors;

@Testcontainers
public class BookDbTest {
    @Container
    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres")
            .withUsername("postgres")
            .withDatabaseName("my_data")
            .withPassword("Fox1997")
            .withInitScript( "test.sql" );
    private static Connection connection;
    private static Statement statement;
    private static SimpleBookRepositoryImpl bookRepository;
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
            bookRepository = new SimpleBookRepositoryImpl(new DataBaseConnect(
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
        Book book = new Book("Оно","Хорор", 1, 1);
        Assertions.assertNotNull(bookRepository.save(book));
    }

    @Test
    @DisplayName("Get by id test")
    void getById() {
        long id = 1;
        Book book=new Book();
        StringBuilder sb = new StringBuilder();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT c.title, c.genre, b.id, p.id " +
                            "FROM books c " +
                            "INNER JOIN libraries b ON c.library_id = b.id " +
                            "INNER JOIN authors p ON c.author_id = p.id " +
                            "WHERE c.id=?;"
            );
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    book.setTitle(rs.getString(1));
                    book.setGenre(rs.getString(2));
                    book.setLibraryId(rs.getLong(3));
                    book.setAuthorId(4);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Book book1 = bookRepository.findById(id);
        Assertions.assertEquals(book1.getTitle(),book.getTitle());
    }

    @Test
    @DisplayName("Delete test")
    void deleteById() {
        long id = 3;
        String titleOfDeletingBook = bookRepository.findById(id).getTitle();
        Assertions.assertTrue(bookRepository.deleteById(id));
        Assertions.assertNotEquals(titleOfDeletingBook, bookRepository.findById(id));
    }
}
