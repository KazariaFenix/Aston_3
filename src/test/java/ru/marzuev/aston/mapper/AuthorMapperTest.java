package ru.marzuev.aston.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.marzuev.aston.model.Author;
import ru.marzuev.aston.model.dto.AuthorDto;
import ru.marzuev.aston.model.dto.BookTitle;
import ru.marzuev.aston.model.mapper.AuthorMapper;
import ru.marzuev.aston.model.mapper.AuthorMapperImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorMapperTest {
    private Author author;
    private AuthorDto authorDto;
    private AuthorMapper authorMapper;

    @BeforeEach
    void setUp() {
        author = new Author(1L, "Ivan", LocalDate.of(1990, 10, 20));
        authorDto = new AuthorDto("Ivan", LocalDate.of(1990, 10, 20),
                null);
        authorMapper = new AuthorMapperImpl();
    }

    @Test
    void mappingToAuthor_whenNormal_thenReturnAuthor() {
        Author mappingAuthor = authorMapper.toAuthor(authorDto);

        assertEquals(author.getDateBorn(), mappingAuthor.getDateBorn());
        assertEquals(author.getListBooks(), mappingAuthor.getListBooks());
        assertEquals(author.getName(), mappingAuthor.getName());
    }

    @Test
    void mappingToAuthorDto_whenNormal_thenReturnAuthor() {
        AuthorDto mappingAuthorDto = authorMapper.toAuthorDto(author);

        assertEquals(authorDto.getDateBorn(), mappingAuthorDto.getDateBorn());
        assertEquals(authorDto.getBooksList(), mappingAuthorDto.getBooksList());
        assertEquals(authorDto.getName(), mappingAuthorDto.getName());
    }
}
