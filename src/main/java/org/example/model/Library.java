package org.example.model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.List;
@JsonAutoDetect
public class Library {
    private long id;
    private String title;
    private List<Book> books;
    private List<Author> authors;
    public Library(){

    }
    public Library(String title){
        this.title=title;}



    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return id + ". " + title;
    }
}

