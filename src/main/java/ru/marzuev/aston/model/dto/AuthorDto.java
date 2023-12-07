package ru.marzuev.aston.model.dto;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class AuthorDto {
    private String name;
    private LocalDate dateBorn;
    private List<String> booksList;

    public AuthorDto() {
    }

    public AuthorDto(String name, LocalDate dateBorn, List<String> booksList) {
        this.name = name;
        this.dateBorn = dateBorn;
        this.booksList = booksList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateBorn() {
        return dateBorn;
    }

    public void setDateBorn(LocalDate dateBorn) {
        this.dateBorn = dateBorn;
    }

    public List<String> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<String> booksList) {
        this.booksList = booksList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDto authorDto = (AuthorDto) o;
        return Objects.equals(name, authorDto.name) && Objects.equals(dateBorn, authorDto.dateBorn)
                && Objects.equals(booksList, authorDto.booksList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateBorn, booksList);
    }

    @Override
    public String toString() {
        return "AuthorDto{" +
                "name='" + name + '\'' +
                ", dateBorn=" + dateBorn +
                ", booksList=" + booksList +
                '}';
    }
}
