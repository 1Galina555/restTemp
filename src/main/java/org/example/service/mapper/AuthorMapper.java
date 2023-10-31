package org.example.service.mapper;
import org.example.model.Author;
import org.example.service.dto.AuthorDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

@Mapper
public interface AuthorMapper {
   AuthorMapper INSTANCE= Mappers.getMapper(AuthorMapper.class);
    AuthorDto toDto(Author author);
    Author fromDto(AuthorDto authorDto);
    default Author map(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setName( resultSet.getString( 2 ) );
        author.setLastName( resultSet.getString( 3 ));
        return author;
    }

}



