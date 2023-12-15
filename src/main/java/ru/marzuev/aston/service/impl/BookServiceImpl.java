package ru.marzuev.aston.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.marzuev.aston.model.Author;
import ru.marzuev.aston.model.Book;
import ru.marzuev.aston.model.dto.BookDto;
import ru.marzuev.aston.model.mapper.BookMapper;
import ru.marzuev.aston.repository.AuthorRepository;
import ru.marzuev.aston.repository.BookRepository;
import ru.marzuev.aston.repository.CommentRepository;
import ru.marzuev.aston.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    BookRepository bookRepository;
    AuthorRepository authorRepository;
    CommentRepository commentRepository;
    BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                           CommentRepository commentRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.commentRepository = commentRepository;
        this.bookMapper = bookMapper;
    }


    @Override
    public BookDto addBook(BookDto bookDto, List<Long> authorsList) {
        Book book = bookMapper.toBook(bookDto);
        List<Author> authors = new ArrayList<>();

        for (Long authorId : authorsList) {
            Author author = authorRepository.findById(authorId).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author Not Found"));
            authors.add(author);
        }
        book.setListAuthors(authors);
        book.setListComments(new ArrayList<>());
        Book saveBook = bookRepository.save(book);

        return bookMapper.toBookDto(saveBook);
    }

    @Override
    public BookDto updateBook(BookDto bookDto, long bookId) {
        Book oldBook = bookRepository.findById(bookId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found"));
        Book updateBook = bookMapper.toBook(bookDto);

        updateBook.setListAuthors(oldBook.getListAuthors());
        updateBook.setListComments(oldBook.getListComments());

        return bookMapper.toBookDto(bookRepository.save(updateBook));
    }

    @Override
    public void deleteBook(long bookId) {
        bookRepository.findById(bookId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found"));
        bookRepository.deleteBooksById(bookId);
    }

    @Override
    public BookDto getBookById(long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found"));

        return bookMapper.toBookDto(book);
    }

    @Override
    public List<BookDto> getBooksByAuthorId(long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author Not Found"));
        List<Book> books = bookRepository.findBooksByAuthorId(author.getId());

        return books.stream()
                .map(book -> bookMapper.toBookDto(book))
                .collect(Collectors.toList());
    }
}
