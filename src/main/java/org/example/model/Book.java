package org.example.model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Book {
    private long id;
    private String title;
    private String genre;
    private long LibraryId;
    private long AuthorId;
    public Book() {
    }

    public Book(String title,String genre, long LibraryId, long AuthorId) {
        this.title = title;
        this.genre = genre;
        this.LibraryId = LibraryId;
        this.AuthorId=AuthorId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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

    @Override
    public String toString() {
        return id + ". " + title + "genre:" + genre;
    }
}
