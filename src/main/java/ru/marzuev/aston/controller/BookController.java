package ru.marzuev.aston.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.marzuev.aston.model.dto.BookDto;
import ru.marzuev.aston.service.BookService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public BookDto postBook(@RequestBody BookDto bookDto,
                            @RequestParam(defaultValue = "null") List<Long> authors) throws SQLException {
        if (authors == null) {
            throw new IllegalArgumentException("Authors Id Invalid");
        }
        return bookService.addBook(bookDto, authors);
    }

    @PutMapping("/{bookId}")
    public BookDto putBook(@RequestBody BookDto bookDto, @PathVariable long bookId) throws SQLException {
        return bookService.updateBook(bookDto, bookId);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable long bookId) throws SQLException {
        bookService.deleteBook(bookId);
    }

    @GetMapping("/{bookId}")
    public BookDto getBookById(@PathVariable long bookId) throws SQLException {
        return bookService.getBookById(bookId);
    }

    @GetMapping
    public List<BookDto> getBooksByAuthorId(@RequestParam(defaultValue = "0") long authorId) throws SQLException {
        if (authorId == 0) {
            throw new IllegalArgumentException("Author Id Invalid");
        }
        return bookService.getBooksByAuthorId(authorId);
    }
}
