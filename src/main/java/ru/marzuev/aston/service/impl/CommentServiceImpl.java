package ru.marzuev.aston.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.marzuev.aston.model.Book;
import ru.marzuev.aston.model.Comment;
import ru.marzuev.aston.model.dto.CommentDto;
import ru.marzuev.aston.model.mapper.CommentMapper;
import ru.marzuev.aston.repository.BookRepository;
import ru.marzuev.aston.repository.CommentRepository;
import ru.marzuev.aston.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;
    BookRepository bookRepository;
    CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository,
                              CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentDto addComment(CommentDto commentDto, long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("Book Not Found")
        );
        Comment comment = commentMapper.toComment(0, commentDto);
        comment.setBook(book);

        Comment saveComment = commentRepository.save(comment);
        //saveComment.setBook(book);

        return commentMapper.toCommentDto(saveComment);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, long commentId) {
        Comment oldComment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("Comment Not Found")
        );
        Comment comment = commentMapper.toComment(commentId, commentDto);
        comment.setBook(oldComment.getBook());

        return commentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("Comment Not Found")
        );

        commentRepository.deleteById(comment.getId());
    }

    @Override
    public List<CommentDto> getCommentsByBookId(long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("Book Not Found")
        );
        List<Comment> bookComments = commentRepository.findCommentsByBook_Id(bookId);

        return bookComments.stream()
                .peek(comment -> comment.setBook(book))
                .map(comment -> commentMapper.toCommentDto(comment))
                .collect(Collectors.toList());
    }
}
