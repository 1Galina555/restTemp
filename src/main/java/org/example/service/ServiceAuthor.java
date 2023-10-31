package org.example.service;
import org.example.service.dto.AuthorDto;

public interface ServiceAuthor {
    AuthorDto findById(long id);
}
