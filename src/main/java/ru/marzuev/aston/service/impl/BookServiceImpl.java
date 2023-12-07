package ru.marzuev.aston.service.impl;

import ru.marzuev.aston.model.Author;
import ru.marzuev.aston.model.Book;
import ru.marzuev.aston.model.Comment;
import ru.marzuev.aston.model.dto.BookDto;
import ru.marzuev.aston.repository.AuthorRepository;
import ru.marzuev.aston.repository.BookRepository;
import ru.marzuev.aston.repository.CommentRepository;
import ru.marzuev.aston.service.BookService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {
    BookRepository bookRepository;
    AuthorRepository authorRepository;
    CommentRepository commentRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                           CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.commentRepository = commentRepository;
    }


    @Override
    public BookDto addBook(BookDto bookDto, List<Long> authorsList) throws SQLException {
        return null;
    }

    @Override
    public BookDto updateBook(BookDto bookDto, long bookId) throws SQLException {
        return null;
    }

    @Override
    public void deleteBook(long bookId) throws SQLException {

    }

    @Override
    public BookDto getBookById(long bookId) throws SQLException {
        return null;
    }

    @Override
    public List<BookDto> getBooksByAuthorId(long authorId) throws SQLException {
        return null;
    }
}
