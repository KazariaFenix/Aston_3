package ru.marzuev.aston.model;


import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private LocalDate release;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "authors_books", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
    private List<Author> listAuthors;
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Comment> listComments;

    public Book() {
    }

    public Book(long id, String title, String description, LocalDate release) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.release = release;
    }

    public Book(long id, String title, String description, LocalDate release, List<Author> listAuthors,
                List<Comment> listComments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.release = release;
        this.listAuthors = listAuthors;
        this.listComments = listComments;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getRelease() {
        return release;
    }

    public void setRelease(LocalDate release) {
        this.release = release;
    }

    public List<Author> getListAuthors() {
        return listAuthors;
    }

    public void setListAuthors(List<Author> listAuthors) {
        this.listAuthors = listAuthors;
    }

    public List<Comment> getListComments() {
        return listComments;
    }

    public void setListComments(List<Comment> listComments) {
        this.listComments = listComments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Objects.equals(title, book.title) && Objects.equals(description, book.description) &&
                Objects.equals(release, book.release) && Objects.equals(listAuthors, book.listAuthors) &&
                Objects.equals(listComments, book.listComments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, release, listAuthors, listComments);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", release=" + release +
                ", listAuthors=" + listAuthors +
                ", listComments=" + listComments +
                '}';
    }
}
