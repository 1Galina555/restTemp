package org.example.model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.List;
import java.util.Objects;

@JsonAutoDetect
public class Author {
    private long id;

    private String name;

    private String lastName;

    private List<Library> libraries;

    private List<Book> books;

    public Author() {
    }

    public Author(String name, String lastName,List<Library> libraries,List<Book>books) {
        this.name = name;
        this.lastName = lastName;
        this.libraries=libraries;
        this.books=books;
    }
    public  Author(String name,String lastName){
        this.name=name;
        this.lastName=lastName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }


    @Override
    public String toString() {
        return id + ". " + name + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name) && Objects.equals(lastName, author.lastName) && Objects.equals(libraries, author.libraries) && Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, libraries, books);
    }
}
