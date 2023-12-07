package ru.marzuev.aston.service.impl;

import ru.marzuev.aston.model.Author;
import ru.marzuev.aston.model.Book;
import ru.marzuev.aston.model.dto.AuthorDto;
import ru.marzuev.aston.repository.AuthorRepository;
import ru.marzuev.aston.repository.BookRepository;
import ru.marzuev.aston.service.AuthorService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthorServiceImpl implements AuthorService {

    AuthorRepository repository;
    BookRepository bookRepository;


    @Override
    public AuthorDto addAuthor(AuthorDto authorDto) throws SQLException {
        return null;
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto, long authorId) throws SQLException {
        return null;
    }

    @Override
    public void deleteAuthor(long authorId) throws SQLException {

    }

    @Override
    public AuthorDto getAuthorById(long authorId) throws SQLException {
        return null;
    }

    @Override
    public List<AuthorDto> getAuthors() throws SQLException {
        return null;
    }
}
