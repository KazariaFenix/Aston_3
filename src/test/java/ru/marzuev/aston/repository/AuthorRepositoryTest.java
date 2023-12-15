package ru.marzuev.aston.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.marzuev.aston.config.PersistenceConfigForTest;
import ru.marzuev.aston.model.Author;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.marzuev.aston.config.PersistenceConfigForTest.postgres;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersistenceConfigForTest.class, AuthorRepository.class})
class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;
    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @AfterEach
    void afterEach() {
        authorRepository.deleteAll();
    }

    @Test
    void saveAuthor_whenNormal_thenReturnAuthor() {
        final Author author = new Author(1L, "Bob", LocalDate.of(1990, 10,28));

        final Author saveAuthor = authorRepository.save(author);

        author.setId(saveAuthor.getId());
        assertEquals(author, saveAuthor);
    }

    @Test
    void saveAuthor_whenDuplicateName_throwNewException() {
        final Author author = new Author(1L, "Bob", LocalDate.of(1990, 10,28));
        final Author duplicate = new Author(2L, "Bob", LocalDate.of(1990, 10,28));

        authorRepository.save(author);
        assertThrows(Exception.class, () -> authorRepository.save(duplicate));
    }

    @Test
    void updateAuthor_whenNormal_thenReturnAuthor() {
        final Author author = new Author(1L, "Bob", LocalDate.of(1990, 10,28));
        final Author newAuthor = new Author(1L, "Alex", LocalDate.of(1990, 10,28));
        authorRepository.save(author);
        final Author updateAuthor = authorRepository.save(newAuthor);

        newAuthor.setId(updateAuthor.getId());
        assertEquals(newAuthor, updateAuthor);
    }

    @Test
    void deleteAuthor_whenNormal_thenDeleteAuthor() {
        final Author author = new Author(1L, "Bob", LocalDate.of(1990, 10,28));
        final Author newAuthor = new Author(2L, "Alex", LocalDate.of(1990, 10,28));

        authorRepository.save(author);
        authorRepository.save(newAuthor);
        authorRepository.deleteById(1L);

        List<Author> authors = authorRepository.findAll();

        assertEquals(1L, authors.size());
    }

    @Test
    void getAuthorById_whenAuthorNotFound_throwNewException() {
        Optional<Author> author = authorRepository.findById(1L);

        assertEquals(author, Optional.empty());
    }

    @Test
    void getAuthorById_whenNormal_thenReturnAuthor() {
        final Author author = new Author(1L, "Bob", LocalDate.of(1990, 10,28));
        final Author saveAuthor = authorRepository.save(author);
        final Optional<Author> findAuthor = authorRepository.findById(saveAuthor.getId());

        saveAuthor.setListBooks(new ArrayList<>());
        assertEquals(Optional.of(saveAuthor), findAuthor);
    }

    @Test
    void getAuthors_whenNormal_thenReturnAuthor() {
        final Author author = new Author(1L, "Bob", LocalDate.of(1990, 10,28));
        final Author newAuthor = new Author(2L, "Alex", LocalDate.of(1990, 10,28));

        authorRepository.save(author);
        authorRepository.save(newAuthor);

        List<Author> authors = authorRepository.findAll();

        assertEquals(2L, authors.size());
    }
}
