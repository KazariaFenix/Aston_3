package ru.marzuev.aston.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.marzuev.aston.config.PersistenceConfigForTest;
import ru.marzuev.aston.model.Author;
import ru.marzuev.aston.model.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.marzuev.aston.config.PersistenceConfigForTest.postgres;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersistenceConfigForTest.class, AuthorRepository.class, BookRepository.class})
class BookRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    private Author author;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void initAuthor() {
        author = new Author(0L, "Bob", LocalDate.of(1990, 10, 25));
        author.setListBooks(new ArrayList<>());
        author = authorRepository.save(author);
    }

    @AfterEach
    void afterEach() {
        authorRepository.deleteById(author.getId());
    }

    @Test
    void saveBook_whenNormal_thenReturnBook() {
        final Book book = new Book(0, "Book", "Description", LocalDate.now());

        book.setListAuthors(List.of(author));
        Book saveBook = bookRepository.save(book);

        assertEquals(saveBook, book);
    }

    @Test
    void saveBook_whenDuplicateName_throwException() {
        final Book book = new Book(0, "Book", "Description", LocalDate.now());
        final Book duplicate = new Book(0, "Book", "Description", LocalDate.now());

        book.setListAuthors(List.of(author));
        duplicate.setListAuthors(List.of(author));

        bookRepository.save(book);

        assertThrows(Exception.class, () -> bookRepository.save(duplicate));
    }

    @Test
    void updateBook_whenNormal_thenReturnBook() {
        final Book book = new Book(0, "Book", "Description", LocalDate.now());
        final Book newBook = new Book(1L, "Stas", "Description", LocalDate.now());

        book.setListAuthors(List.of(author));
        newBook.setListAuthors(List.of(author));
        bookRepository.save(book);

        Book updateBook = bookRepository.save(newBook);

        newBook.setId(updateBook.getId());
        assertEquals(updateBook.getTitle(), newBook.getTitle());
        assertEquals(updateBook.getDescription(), newBook.getDescription());
        assertEquals(updateBook.getId(), newBook.getId());
    }

    @Test
    void getBookById_whenNormal_thenReturnBook() {
        final Book book = new Book(0, "Book", "Description", LocalDate.now());
        final Book saveBook = bookRepository.save(book);
        final Book findBook = bookRepository.findById(saveBook.getId()).orElseThrow();

        assertEquals(saveBook.getId(), findBook.getId());
    }

    @Test
    void getBookById_whenBookNotFound_thenReturnNull() {
        Optional<Book> findBook = bookRepository.findById(1L);

        assertEquals(findBook, Optional.empty());
    }

    @Test
    void getBooksByAuthorId_whenNormal_thenReturnBooks() {
        final Book book = new Book(0, "Book", "Description", LocalDate.now());
        final Book book1 = new Book(0, "Book1", "Description", LocalDate.now());

        book.setListAuthors(List.of(author));
        book1.setListAuthors(List.of(author));
        bookRepository.save(book);
        bookRepository.save(book1);

        List<Book> books = bookRepository.findBooksByAuthorId(author.getId());

        assertEquals(2, books.size());


    }
}
