package ru.marzuev.aston.model.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class BookDto {
    private String title;
    private String description;
    private LocalDate release;
    private List<AuthorDto> listAuthors;
    private List<CommentDto> listComments;

    public BookDto() {
    }

    public BookDto(String title, String description, LocalDate release, List<AuthorDto> listAuthors,
                   List<CommentDto> listComments) {
        this.title = title;
        this.description = description;
        this.release = release;
        this.listAuthors = listAuthors;
        this.listComments = listComments;
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

    public List<AuthorDto> getListAuthors() {
        return listAuthors;
    }

    public void setListAuthors(List<AuthorDto> listAuthors) {
        this.listAuthors = listAuthors;
    }

    public List<CommentDto> getListComments() {
        return listComments;
    }

    public void setListComments(List<CommentDto> listComments) {
        this.listComments = listComments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(title, bookDto.title) && Objects.equals(description, bookDto.description) &&
                Objects.equals(release, bookDto.release) && Objects.equals(listAuthors, bookDto.listAuthors) &&
                Objects.equals(listComments, bookDto.listComments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, release, listAuthors, listComments);
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", release=" + release +
                ", authors=" + listAuthors +
                ", comments=" + listComments +
                '}';
    }
}
