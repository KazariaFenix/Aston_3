package ru.marzuev.aston.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.marzuev.aston.model.Book;
import ru.marzuev.aston.model.dto.BookDto;
import ru.marzuev.aston.model.mapper.BookMapper;
import ru.marzuev.aston.model.mapper.BookMapperImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookMapperTest {
    private Book book;
    private BookDto bookDto;

    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "Title", "Desc", LocalDate.now());
        bookDto = new BookDto("Title", "Desc", LocalDate.now(), null, null);
        bookMapper = new BookMapperImpl();
    }

    @Test
    void mappingToBook_whenNormal_thenReturnBook() {
        Book mappingBook = bookMapper.toBook(bookDto);

        assertEquals(book.getDescription(), mappingBook.getDescription());
        assertEquals(book.getListAuthors(), mappingBook.getListAuthors());
        assertEquals(book.getTitle(), mappingBook.getTitle());
        assertEquals(book.getRelease(), mappingBook.getRelease());
    }

    @Test
    void mappingToBookDto_whenNormal_thenReturnBookDto() {
        BookDto mappingBookDto = bookMapper.toBookDto(book);

        assertEquals(bookDto.getDescription(), mappingBookDto.getDescription());
        assertEquals(bookDto.getListAuthors(), mappingBookDto.getListAuthors());
        assertEquals(bookDto.getTitle(), mappingBookDto.getTitle());
        assertEquals(bookDto.getRelease(), mappingBookDto.getRelease());
    }
}
