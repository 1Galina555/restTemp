package Repository;

import org.example.db.DataBaseConnect;
import org.example.model.Author;
import org.example.model.Book;
import org.example.repository.impl.SimpleAuthorRepositoryImpl;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Testcontainers
public class AuthorDbTest {
    @Container
    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withUsername("postgres")
            .withDatabaseName("my_data")
            .withPassword("Fox1997")
            .withInitScript( "test.sql" );

    private static Connection connection;
    private static Statement statement;
    private static SimpleAuthorRepositoryImpl authorRepository;

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
            authorRepository = new SimpleAuthorRepositoryImpl(new DataBaseConnect(
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
    @AfterAll
    public static void disconnect() {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
            System.out.println("Disconnected");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("Add test")
    void add() {
        Author author = new Author("New", "Person");
        String a=authorRepository.save(author).toString();
        Assertions.assertEquals(a,authorRepository.save(author).toString());
    }
    @Test
    @DisplayName("Get by id test")
    void getById() {
        long id = 1;
        String name = "";
        String lastName = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM authors WHERE id=?;"
            );
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    name = rs.getString(2);
                    lastName = rs.getString(3);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(id + ". " + name + " " + lastName, authorRepository.findById(id).toString());
    }

    @Test
    @DisplayName("Get by name and lastName test")
    void getByFullName() {
        Author author=new Author();
        StringBuilder sb = new StringBuilder();
        String name = "Stephen";
        String lastName = "King";
        long id = 1;
        author.setName(name);
        author.setLastName(lastName);
        author.setId(id);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id FROM authors WHERE (name, lastname) = (?, ?);"
            );
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    id = rs.getLong(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(author.getName(),authorRepository.getByName(name,lastName).getName());
    }
    @Test
    @DisplayName("Get list of authors")
    void getListOfAuthors() {
        //StringBuilder sb = new StringBuilder();
        List<Author>authors1=new ArrayList<>();
        try (ResultSet rs = statement.executeQuery("SELECT * FROM authors;")) {
            while (rs.next()) {
                Author author=new Author();
                author.setId(rs.getLong(1));
                author.setName(rs.getString(2));
                author.setLastName(rs.getString(3));
                authors1.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Author> authors=authorRepository.findAll();
        Assertions.assertEquals(authors1.size(), authors.size());
    }
    @Test
    @DisplayName("Update test")
    void updateById() {
        String id = "1";
        String newName = "Charles";
        String newLastName = "Dickens";
        Assertions.assertTrue(authorRepository.updateById(Long.parseLong(id), newName, newLastName));
        Assertions.assertEquals(
                Long.parseLong(id) + ". " + newName + " " + newLastName,
                authorRepository.findById(Long.parseLong(id)).toString()
        );
    }

    @Test
    @DisplayName("Delete test")
    void deleteById() {
        long id = 3;
        String fullNameOfDeletingAuthor = authorRepository.findById(id).toString();
        Assertions.assertTrue(authorRepository.deleteByID(id));
        Assertions.assertNotEquals(
                fullNameOfDeletingAuthor, authorRepository.findById(id).toString()
        );
    }
}
