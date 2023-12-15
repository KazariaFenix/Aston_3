package ru.marzuev.aston.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.marzuev.aston.model.Author;
import ru.marzuev.aston.model.dto.AuthorDto;
import ru.marzuev.aston.model.mapper.AuthorMapper;
import ru.marzuev.aston.repository.AuthorRepository;
import ru.marzuev.aston.service.AuthorService;

import java.util.List;
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
        Author author = authorMapper.toAuthor(authorDto);

        return authorMapper.toAuthorDto(repository.save(author));
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto, long authorId) {
        repository.findById(authorId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author Not Found"));

        Author author = repository.save(authorMapper.toAuthor(authorDto));

        return authorMapper.toAuthorDto(author);
    }

    @Override
    public void deleteAuthor(long authorId) {
        repository.findById(authorId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author Not Found"));
        repository.deleteById(authorId);
    }

    @Override
    public AuthorDto getAuthorById(long authorId) {
        Author author = repository.findById(authorId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author Not Found"));

        return authorMapper.toAuthorDto(author);
    }

    @Override
    public List<AuthorDto> getAuthors() {
        List<Author> authors = repository.findAll();

        return authors.stream()
                .map(author -> authorMapper.toAuthorDto(author))
                .collect(Collectors.toList());
    }
}
