package ru.marzuev.aston.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.marzuev.aston.model.Author;
import ru.marzuev.aston.model.Book;
import ru.marzuev.aston.model.dto.AuthorDto;
import ru.marzuev.aston.model.mapper.AuthorMapper;
import ru.marzuev.aston.repository.AuthorRepository;
import ru.marzuev.aston.repository.BookRepository;
import ru.marzuev.aston.service.AuthorService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository repository;
    private AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository, AuthorMapper authorMapper) {
        this.repository = repository;
        this.authorMapper = authorMapper;
    }

    @Override
    public AuthorDto addAuthor(AuthorDto authorDto) {
        Author author = authorMapper.toAuthor(0, authorDto);

        return authorMapper.toAuthorDto(repository.save(author));
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto, long authorId) {
        getAuthorById(authorId);
        Author author = repository.save(authorMapper.toAuthor(authorId, authorDto));

        return authorMapper.toAuthorDto(author);
    }

    @Override
    public void deleteAuthor(long authorId) {
        getAuthorById(authorId);
        repository.deleteById(authorId);
    }

    @Override
    public AuthorDto getAuthorById(long authorId) {
        Author author = repository.findById(authorId).orElseThrow(() -> new IllegalArgumentException());

        return authorMapper.toAuthorDto(author);
    }

    @Override
    public List<AuthorDto> getAuthors() {
        Map<Author, List<Book>> authorWithBooks = repository.getAuthorsWithBooks();
        return authorWithBooks.entrySet().stream()
                .peek(entry -> entry.getKey().setListBooks(entry.getValue()))
                .map(entry -> authorMapper.toAuthorDto(entry.getKey()))
                .collect(Collectors.toList());
    }
}
