package ru.marzuev.aston.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.marzuev.aston.model.Author;
import ru.marzuev.aston.model.Book;
import ru.marzuev.aston.model.dto.AuthorDto;
import ru.marzuev.aston.model.dto.BookTitle;
import ru.marzuev.aston.model.mapper.AuthorMapper;
import ru.marzuev.aston.repository.AuthorRepository;
import ru.marzuev.aston.repository.BookRepository;
import ru.marzuev.aston.service.impl.AuthorServiceImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    @InjectMocks
    AuthorServiceImpl authorService;
    @Mock
    AuthorRepository authorRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorMapper authorMapper;

    @Test
    void addAuthor_whenNormal_thenReturnAuthor() throws SQLException {
        final AuthorDto authorDto = new AuthorDto("Bob", LocalDate.of(1990, 10,28),
                new ArrayList<>());
        final Author author = new Author(0L, "Bob", LocalDate.of(1990, 10,28));

        Mockito
                .when(authorRepository.save(author))
                .thenReturn(author);
        Mockito
                .doReturn(author)
                .when(authorMapper).toAuthor(authorDto);
        Mockito
                .doReturn(authorDto)
                .when(authorMapper).toAuthorDto(author);

        AuthorDto savedAuthor = authorService.addAuthor(authorDto);

        assertThat(authorDto, equalTo(savedAuthor));
        Mockito.verify(authorRepository, Mockito.times(1)).save(any());
    }

    @Test
    void updateAuthor_whenNormal_thenReturnUpdateAuthor() throws SQLException {
        final AuthorDto authorDto = new AuthorDto("UpdateBob", LocalDate.of(1990, 10,28),
                new ArrayList<>());
        final Author newAuthor = new Author(1L, "UpdateBob", LocalDate.of(1990, 10,28));

        Mockito
                .when(authorRepository.save(newAuthor))
                .thenReturn(newAuthor);
        Mockito
                .doReturn(newAuthor)
                .when(authorMapper).toAuthor(authorDto);
        Mockito
                .doReturn(authorDto)
                .when(authorMapper).toAuthorDto(newAuthor);
        Mockito
                .doReturn(Optional.of(newAuthor))
                .when(authorRepository).findById(newAuthor.getId());

        AuthorDto updateAuthor = authorService.updateAuthor(authorDto, 1);
        assertThat(authorDto, equalTo(updateAuthor));
        Mockito.verify(authorRepository, Mockito.times(1)).save(any());
    }

    @Test
    void updateAuthor_whenNotFoundAuthor_thenReturnUpdateAuthor() throws SQLException {
        final AuthorDto authorDto = new AuthorDto("UpdateBob", LocalDate.of(1990, 10,28),
                new ArrayList<>());
        final Author author = new Author(1L, "Bob", LocalDate.of(1990, 10,28));

        Mockito
                .when(authorRepository.findById(author.getId()))
                .thenReturn(Optional.empty());

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> authorService.updateAuthor(authorDto, 1));
        assertThat(e.getMessage(), equalTo("Author Not Found"));
        Mockito.verify(authorRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteAuthor_whenNormal_thenDeleteAuthor() throws SQLException {
        final Author newAuthor = new Author(1L, "Bob", LocalDate.of(1990, 10,28));

        Mockito
                .doNothing()
                .when(authorRepository).deleteById(newAuthor.getId());
        Mockito
                .doReturn(Optional.of(newAuthor))
                .when(authorRepository).findById(newAuthor.getId());
        authorService.deleteAuthor(newAuthor.getId());
        Mockito.verify(authorRepository, Mockito.times(1)).deleteById(newAuthor.getId());
    }

    @Test
    void deleteAuthor_whenNotFoundAuthor_thenDeleteAuthor() throws SQLException {
        final Author newAuthor = new Author(1L, "Bob", LocalDate.of(1990, 10,28));

        Mockito
                .doReturn(Optional.empty())
                .when(authorRepository).findById(newAuthor.getId());
        IllegalArgumentException e  = assertThrows(IllegalArgumentException.class,
                () -> authorService.deleteAuthor(newAuthor.getId()));

        assertThat(e.getMessage(), equalTo("Author Not Found"));
        Mockito.verify(authorRepository, Mockito.times(1)).findById(newAuthor.getId());
    }

    @Test
    void getAuthorById_whenNormal_thenReturnAuthor() throws SQLException {
        final AuthorDto authorDto = new AuthorDto("Bob", LocalDate.of(1990, 10,28),
                new ArrayList<>());
        final Author newAuthor = new Author(1L, "Bob", LocalDate.of(1990, 10,28));

        Mockito
                .when(authorRepository.findById(newAuthor.getId()))
                .thenReturn(Optional.of(newAuthor));
        Mockito
                .doReturn(authorDto)
                .when(authorMapper).toAuthorDto(newAuthor);

        AuthorDto author = authorService.getAuthorById(newAuthor.getId());
        assertThat(author, equalTo(authorDto));
        Mockito.verify(authorRepository, Mockito.times(1)).findById(newAuthor.getId());
    }

    @Test
    void getAuthorById_whenNotFoundAuthor_thenReturnAuthor() throws SQLException {
        final Author newAuthor = new Author(1L, "Bob", LocalDate.of(1990, 10,28));

        Mockito
                .when(authorRepository.findById(newAuthor.getId()))
                .thenReturn(Optional.empty());

        IllegalArgumentException e  = assertThrows(IllegalArgumentException.class,
                () -> authorService.deleteAuthor(newAuthor.getId()));

        assertThat(e.getMessage(), equalTo("Author Not Found"));
        Mockito.verify(authorRepository, Mockito.times(1)).findById(newAuthor.getId());
    }

    @Test
    void getAuthors_whenNormal_thenReturnAuthor() throws SQLException {
        final Author newAuthor1 = new Author(1L, "Bob", LocalDate.of(1990, 10,28));
        final Author newAuthor = new Author(2L, "Alex", LocalDate.of(1990, 10,28));
        final AuthorDto authorDto = new AuthorDto("Bob", LocalDate.of(1990, 10,28),
                new ArrayList<>());
        final AuthorDto authorDto1 = new AuthorDto("Alex", LocalDate.of(1990, 10,28),
                new ArrayList<>());

        Mockito
                .when(authorRepository.findAll())
                .thenReturn(List.of(newAuthor, newAuthor1));
        Mockito
                .doReturn(authorDto)
                .when(authorMapper).toAuthorDto(newAuthor);
        Mockito
                .doReturn(authorDto1)
                .when(authorMapper).toAuthorDto(newAuthor1);

        List<AuthorDto> listAuthors = authorService.getAuthors();

        assertThat(listAuthors.size(), equalTo(2));
        Mockito.verify(authorRepository, Mockito.times(1)).findAll();
    }
}
