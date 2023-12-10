package ru.marzuev.aston.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.marzuev.aston.model.Author;
import ru.marzuev.aston.model.Book;
import ru.marzuev.aston.model.Comment;
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

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                           CommentRepository commentRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.commentRepository = commentRepository;
        this.bookMapper = bookMapper;
    }


    @Override
    public BookDto addBook(BookDto bookDto, List<Long> authorsList) {
        List<Author> authors = new ArrayList<>();

        for (Long authorId : authorsList) {
            authors.add(authorRepository.findById(authorId).orElseThrow(() -> new IllegalArgumentException()));
        }
        Book book = bookRepository.save(bookMapper.toBook(0L, bookDto));
        book.setListAuthors(authors);
        book.setListComments(new ArrayList<>());

        return bookMapper.toBookDto(book);
    }

    @Override
    public BookDto updateBook(BookDto bookDto, long bookId) {
        getBookById(bookId);
        Book updateBook = bookRepository.save(bookMapper.toBook(bookId, bookDto));
        List<Author> authors = authorRepository.findAuthorByBookId(bookId);
        List<Comment> comments = commentRepository.findCommentsByBook_Id(bookId);
        comments.stream()
                .peek(comment -> comment.setBook(updateBook))
                .collect(Collectors.toList());
        updateBook.setListAuthors(authors);
        updateBook.setListComments(comments);

        return bookMapper.toBookDto(updateBook);
    }

    @Override
    public void deleteBook(long bookId) {
        getBookById(bookId);
        bookRepository.deleteById(bookId);
    }

    @Override
    public BookDto getBookById(long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book Not Found"));
        List<Author> authors = authorRepository.findAuthorByBookId(bookId);
        List<Comment> comments = commentRepository.findCommentsByBook_Id(book.getId());
        comments.stream()
                .peek(comment -> comment.setBook(book))
                .collect(Collectors.toList());
        book.setListComments(comments);
        book.setListAuthors(authors);

        return bookMapper.toBookDto(book);
    }

    @Override
    public List<BookDto> getBooksByAuthorId(long authorId) {
        List<Book> books = bookRepository.findBooksByAuthorId(authorId);
        Author author = authorRepository.findById(authorId).orElseThrow(
                () -> new IllegalArgumentException("Author Not Found"));
        List<Comment> booksComments = commentRepository.findCommentsByBook_Id(authorId);
        books = books.stream()
                .peek(book -> book.setListAuthors(List.of(author)))
                .collect(Collectors.toList());

        for (Book book : books) {
            List<Comment> bookComments = new ArrayList<>();
            for (Comment comment : booksComments) {
                if (comment.getBook().getId() == book.getId()) {
                    bookComments.add(comment);
                }
            }
            book.setListComments(bookComments);
            bookComments.stream()
                    .peek(comment -> comment.setBook(book))
                    .collect(Collectors.toList());
        }
        return books.stream()
                .map(book -> bookMapper.toBookDto(book))
                .collect(Collectors.toList());
    }
}
