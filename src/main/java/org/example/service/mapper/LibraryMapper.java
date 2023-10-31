package org.example.service.mapper;
import org.example.model.Book;
import org.example.model.Library;
import org.example.service.dto.LibraryDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

@Mapper
public interface LibraryMapper {
    LibraryMapper INSTANCE= Mappers.getMapper(LibraryMapper.class);
    LibraryDto toDto(Library library);
    Library fromDto(LibraryDto libraryDto);
    default Library map(ResultSet resultSet) throws SQLException {
       Library library = new Library();
        library.setTitle( resultSet.getString( 2 ) );
        return library;
    }
}
