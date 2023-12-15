package ru.marzuev.aston.model.dto;

import java.util.Objects;

public class BookTitle {
    private String title;


    public BookTitle() {
    }

    public BookTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookTitle bookTitle = (BookTitle) o;
        return Objects.equals(title, bookTitle.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "BookTitle{" +
                "title='" + title + '\'' +
                '}';
    }
}
