package Mapper;

import org.example.model.Author;
import org.example.model.Library;
import org.example.service.mapper.AuthorMapper;
import org.example.service.mapper.LibraryMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class LibraryMapperTest {
    @Test
    void testMap() throws SQLException {
        ResultSet mockResultSet = Mockito.mock(ResultSet.class );
        String expectedTitle="SmartBook";
        when( mockResultSet.getString( 2) ).thenReturn( expectedTitle );
        LibraryMapper mapper = Mappers.getMapper( LibraryMapper.class );
        Library library = mapper.map(mockResultSet );
        assertEquals( expectedTitle, library.getTitle() );


    }
}
