package ru.marzuev.aston.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import ru.marzuev.aston.model.Author;
import ru.marzuev.aston.model.Book;
import ru.marzuev.aston.model.dto.AuthorDto;
import ru.marzuev.aston.model.dto.BookDto;
import ru.marzuev.aston.model.dto.BookTitle;
import ru.marzuev.aston.model.mapper.BookMapper;
import ru.marzuev.aston.repository.AuthorRepository;
import ru.marzuev.aston.repository.BookRepository;
import ru.marzuev.aston.repository.CommentRepository;
import ru.marzuev.aston.service.impl.BookServiceImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @InjectMocks
    BookServiceImpl bookService;
    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorRepository authorRepository;

    @Mock
    BookMapper bookMapper;

    @Test
    void addBook_whenNormal_thenReturnBook() throws SQLException {
        final Author author = new Author(1L, "Bob", LocalDate.of(1990, 10, 28));
        final Book book = new Book(0, "Book", "Description", LocalDate.now());
        final BookDto bookDto = new BookDto("Book", "Description", LocalDate.now(),
                new ArrayList<>(), new ArrayList<>());
        final AuthorDto authorDto = new AuthorDto("Bob", LocalDate.of(1990, 10, 28),
                List.of(new BookTitle(bookDto.getTitle())));
        final BookDto bookDtoExpected = new BookDto("Book", "Description", LocalDate.now(),
                List.of(authorDto), new ArrayList<>());

        bookDto.setListAuthors(List.of(authorDto));
        Mockito
                .doReturn(book)
                .when(bookRepository).save(book);
        Mockito
                .doReturn(Optional.of(author))
                .when(authorRepository).findById(1L);

        Mockito
                .doReturn(book)
                .when(bookMapper).toBook(bookDto);
        Mockito
                .doReturn(bookDto)
                .when(bookMapper).toBookDto(book);

        BookDto saveBook = bookService.addBook(bookDto, List.of(1L));
        assertThat(bookDtoExpected, equalTo(saveBook));
        Mockito.verify(authorRepository, Mockito.times(1))
                .findById(1L);
    }

    @Test
    void addBook_whenAuthorNotFound_thenThrowException() throws SQLException {
        final Author author = new Author(1L, "Bob", LocalDate.of(1990, 10, 28));
        final BookDto bookDto = new BookDto("Book", "Description", LocalDate.now(),
                new ArrayList<>(), new ArrayList<>());
        Mockito
                .when(authorRepository.findById(author.getId()))
                .thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> bookService.addBook(bookDto, List.of(author.getId())));

        assertThat(e.getMessage(), equalTo("404 NOT_FOUND \"Author Not Found\""));
        Mockito.verify(authorRepository, Mockito.times(1))
                .findById(author.getId());
    }

    @Test
    void updateBook_whenNormal_thenReturnUpdateBook() throws SQLException {
        final Author author = new Author(1L, "Bob", LocalDate.of(1990, 10, 28));
        final Book book = new Book(1L, "Book", "Description", LocalDate.now());
        final BookDto bookDto = new BookDto("Book", "Description", LocalDate.now(),
                new ArrayList<>(), new ArrayList<>());
        final AuthorDto authorDto = new AuthorDto("Bob", LocalDate.of(1990, 10, 28),
                List.of(new BookTitle(bookDto.getTitle())));
        final BookDto bookDtoExpected = new BookDto("Book", "Description", LocalDate.now(),
                List.of(authorDto), new ArrayList<>());

        bookDto.setListAuthors(List.of(authorDto));
        Mockito
                .doReturn(book)
                .when(bookRepository).save(book);
        Mockito
                .doReturn(Optional.of(book))
                .when(bookRepository).findById(1L);
        Mockito
                .doReturn(book)
                .when(bookMapper).toBook(bookDto);
        Mockito
                .doReturn(bookDto)
                .when(bookMapper).toBookDto(book);

        BookDto updateBook = bookService.updateBook(bookDtoExpected, book.getId());
        assertThat(updateBook, equalTo(bookDto));
        Mockito.verify(bookRepository, Mockito.times(1)).findById(book.getId());
    }

    @Test
    void updateBook_whenBookNotFound_thenThrowException() throws SQLException {
        final Book book = new Book(1, "Book", "Description", LocalDate.now());
        final BookDto bookDto = new BookDto("Book", "Description", LocalDate.now(),
                new ArrayList<>(), new ArrayList<>());
        Mockito
                .when(bookRepository.findById(book.getId()))
                .thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> bookService.updateBook(bookDto, book.getId()));

        assertThat(e.getMessage(), equalTo("404 NOT_FOUND \"Book Not Found\""));
        Mockito.verify(bookRepository, Mockito.times(1))
                .findById(book.getId());
    }

    @Test
    void deleteBook_whenNormal_thenDeleteBookk() throws SQLException {
        final Book book = new Book(1, "Book", "Description", LocalDate.now());
        final long bookId = 1L;

        Mockito
                .doNothing()
                .when(bookRepository).deleteBooksById(bookId);
        Mockito
                .doReturn(Optional.of(book))
                        .when(bookRepository).findById(bookId);

        bookService.deleteBook(bookId);
        Mockito.verify(bookRepository, Mockito.times(1))
                .deleteBooksById(bookId);
    }

    @Test
    void deleteBook_whenBookNotFound_thenDeleteBook() throws SQLException {
        final long bookId = 1L;
        Mockito
                .when(bookRepository.findById(bookId))
                .thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> bookService.deleteBook(bookId));
        assertThat(e.getMessage(), equalTo("404 NOT_FOUND \"Book Not Found\""));
        Mockito.verify(bookRepository, Mockito.times(1))
                .findById(bookId);
    }

    @Test
    void getBookById_whenNormal_thenReturnBook() throws SQLException {
        final long bookId = 1L;
        final Book book = new Book(1, "Book", "Description", LocalDate.now());
        final BookDto bookDto = new BookDto("Book", "Description", LocalDate.now(),
                new ArrayList<>(), new ArrayList<>());

        Mockito
                .when(bookRepository.findById(bookId))
                .thenReturn(Optional.of(book));
        Mockito
                .doReturn(bookDto)
                .when(bookMapper).toBookDto(book);

        BookDto findBook = bookService.getBookById(bookId);
        assertThat(bookDto, equalTo(findBook));
        Mockito.verify(bookRepository, Mockito.times(1))
                .findById(bookId);
    }

    @Test
    void getBookById_whenBookNotFound_thenThrowException() throws SQLException {
        final long bookId = 1L;
        Mockito
                .when(bookRepository.findById(bookId))
                .thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> bookService.getBookById(bookId));
        assertThat(e.getMessage(), equalTo("404 NOT_FOUND \"Book Not Found\""));
        Mockito.verify(bookRepository, Mockito.times(1))
                .findById(bookId);
    }

    @Test
    void getBooksByAuthorId_whenNormal_thenReturnBook() throws SQLException {
        final Author author = new Author(1L, "Bob", LocalDate.of(1990, 10, 28));
        final BookDto bookDto = new BookDto("Book", "Description", LocalDate.now(),
                new ArrayList<>(), new ArrayList<>());
        final BookDto bookDto1 = new BookDto("Book1", "Description", LocalDate.now(),
                new ArrayList<>(), new ArrayList<>());
        final Book book = new Book(1, "Book", "Description", LocalDate.now());
        final Book book1 = new Book(2, "Book1", "Description", LocalDate.now());
        Mockito
                .doReturn(List.of(book, book1))
                .when(bookRepository).findBooksByAuthorId(author.getId());
        Mockito
                .doReturn(Optional.of(author))
                .when(authorRepository).findById(author.getId());
        Mockito
                .doReturn(bookDto)
                .when(bookMapper).toBookDto(book);
        Mockito
                .doReturn(bookDto1)
                .when(bookMapper).toBookDto(book1);

        List<BookDto> booksDtoByAuthorId = bookService.getBooksByAuthorId(author.getId());
        assertThat(booksDtoByAuthorId.size(), equalTo(2));
        Mockito.verify(bookRepository, Mockito.times(1)).findBooksByAuthorId(author.getId());
    }

    @Test
    void getBooksByAuthorId_whenAuthorNotFound_thenReturnBook() throws SQLException {
        final long authorId = 1;
        Mockito
                .doReturn(Optional.empty())
                .when(authorRepository).findById(authorId);

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> bookService.getBooksByAuthorId(authorId));
        assertThat(e.getMessage(), equalTo("404 NOT_FOUND \"Author Not Found\""));
        Mockito.verify(authorRepository, Mockito.times(1))
                .findById(authorId);
    }
}
