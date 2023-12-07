package ru.marzuev.aston.service.impl;

import ru.marzuev.aston.model.Book;
import ru.marzuev.aston.model.Comment;
import ru.marzuev.aston.model.dto.CommentDto;
import ru.marzuev.aston.repository.BookRepository;
import ru.marzuev.aston.repository.CommentRepository;
import ru.marzuev.aston.service.CommentService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;
    BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public CommentDto addComment(CommentDto commentDto, long bookId) throws SQLException {
        return null;
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, long commentId) throws SQLException {
        return null;
    }

    @Override
    public void deleteComment(long commentId) throws SQLException {

    }

    @Override
    public List<CommentDto> getCommentsByBookId(long bookId) throws SQLException {
        return null;
    }
}
