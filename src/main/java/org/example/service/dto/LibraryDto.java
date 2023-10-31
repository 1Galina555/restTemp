package org.example.service.dto;
import java.util.List;
public class LibraryDto {
    private long id;
    private String title;
    private List<BookDto> books;
    private List<AuthorDto> authors;

    public LibraryDto(){
    }

    public LibraryDto(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public LibraryDto(long id, String title, List<BookDto> books, List<AuthorDto> authors) {
        this.id = id;
        this.title = title;
        this.books = books;
        this.authors = authors;
    }
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<BookDto> getBooks() {
        return books;
    }

    public List<AuthorDto> getAuthors() {
        return authors;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBooks(List<BookDto> books) {
        this.books = books;
    }

    public void setAuthors(List<AuthorDto> authors) {
        this.authors = authors;
    }
}
