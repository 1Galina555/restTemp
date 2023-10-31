package org.example.service.dto;
public class BookDto {
    private long id;
    private String title;
    private String genre;
    private long LibraryId;
    private long AuthorId;

    public BookDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public long getLibraryId() {
        return LibraryId;
    }

    public void setLibraryId(long libraryId) {
        LibraryId = libraryId;
    }

    public long getAuthorId() {
        return AuthorId;
    }

    public void setAuthorId(long authorId) {
        AuthorId = authorId;
    }
}
