package ru.marzuev.aston.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.marzuev.aston.model.dto.AuthorDto;
import ru.marzuev.aston.service.AuthorService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public AuthorDto postAuthor(@RequestBody AuthorDto authorDto) throws SQLException {
        return authorService.addAuthor(authorDto);
    }

    @PutMapping("/{authorId}")
    public AuthorDto putAuthor(@RequestBody AuthorDto authorDto, @PathVariable long authorId) throws SQLException {
        return authorService.updateAuthor(authorDto, authorId);
    }

    @DeleteMapping("/{authorId}")
    public void deleteAuthor(@PathVariable long authorId) throws SQLException {
        authorService.deleteAuthor(authorId);
    }

    @GetMapping("/{authorId}")
    public AuthorDto getAuthorById(@PathVariable long authorId) throws SQLException {
        return authorService.getAuthorById(authorId);
    }

    @GetMapping
    public List<AuthorDto> getAuthors() throws SQLException {
        return authorService.getAuthors();
    }
}
