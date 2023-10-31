package Mapper;

import org.example.model.Author;
import org.example.model.Book;
import org.example.service.mapper.AuthorMapper;
import org.example.service.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BookMapperTest {
    @Test
    void testMap() throws SQLException {
        ResultSet mockResultSet = Mockito.mock(ResultSet.class );
        String expectedTitle="Мастер и Маргарита";
        String expectedGenre = "роман";
        when( mockResultSet.getString( 2) ).thenReturn( expectedTitle);
        when( mockResultSet.getString( 3) ).thenReturn( expectedGenre);
        BookMapper mapper = Mappers.getMapper( BookMapper.class );
        Book book = mapper.map(mockResultSet );
        assertEquals( expectedTitle, book.getTitle() );
        assertEquals( expectedGenre, book.getGenre() );
    }
}
