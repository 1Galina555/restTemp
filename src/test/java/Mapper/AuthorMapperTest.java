package Mapper;

import org.example.model.Author;
import org.example.service.dto.AuthorDto;
import org.example.service.mapper.AuthorMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AuthorMapperTest {
    @Test
    void testMap() throws SQLException {
        ResultSet mockResultSet = Mockito.mock(ResultSet.class );
        String expectedLastName="Esenin";
        String expectedName = "Sergey";
        when( mockResultSet.getString( 2) ).thenReturn( expectedName );
        when( mockResultSet.getString( 3) ).thenReturn( expectedLastName);
        AuthorMapper mapper = Mappers.getMapper( AuthorMapper.class );
        Author author = mapper.map(mockResultSet );
        assertEquals( expectedName, author.getName() );
        assertEquals( expectedLastName, author.getLastName() );

    }
}
