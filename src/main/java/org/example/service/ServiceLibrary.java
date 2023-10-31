package org.example.service;
import org.example.service.dto.LibraryDto;
public interface ServiceLibrary {

    LibraryDto save(LibraryDto libraryDto);

    LibraryDto findById(long id);
}
