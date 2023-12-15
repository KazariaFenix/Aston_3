package ru.marzuev.aston.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.marzuev.aston.model.Book;
import ru.marzuev.aston.model.dto.BookTitle;
import ru.marzuev.aston.model.mapper.BookTitleMapper;
import ru.marzuev.aston.model.mapper.BookTitleMapperImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookTitleMapperTest {
    private BookTitle bookTitle;
    private Book book;
    private BookTitleMapper bookTitleMapper;

    @BeforeEach
    void setUp() {
        bookTitle = new BookTitle("Book");
        book = new Book(1L, "Book", "Desc", LocalDate.now());
        bookTitleMapper = new BookTitleMapperImpl();
    }

    @Test
    void mappingToTitleBook_whenNormal_thenReturnBookTitle() {
        BookTitle mapping = bookTitleMapper.toBookTitle(book);

        assertEquals(mapping, bookTitle);
    }

}
