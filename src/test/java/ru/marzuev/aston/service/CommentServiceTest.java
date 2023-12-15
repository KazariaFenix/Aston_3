package ru.marzuev.aston.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import ru.marzuev.aston.model.Book;
import ru.marzuev.aston.model.Comment;
import ru.marzuev.aston.model.dto.CommentDto;
import ru.marzuev.aston.model.mapper.CommentMapper;
import ru.marzuev.aston.repository.BookRepository;
import ru.marzuev.aston.repository.CommentRepository;
import ru.marzuev.aston.service.impl.CommentServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @InjectMocks
    CommentServiceImpl commentService;
    @Mock
    CommentRepository commentRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    CommentMapper commentMapper;

    @Test
    void addComment_whenNormal_thenReturnComment() {
        final Book book = new Book(1L, "Title", "Description", LocalDate.now());
        final Optional<Book> optional = Optional.of(book);
        final Comment comment = new Comment(0, "Content");
        final Comment postComment = new Comment(1, "Content");
        final CommentDto commentDto = new CommentDto("Title", "Content");
        final CommentDto addComment = new CommentDto("Title", "Content");
        final long bookId = 1L;
        postComment.setBook(book);

        Mockito
                .doReturn(postComment)
                .when(commentRepository).save(comment);
        Mockito
                .doReturn(optional)
                .when(bookRepository).findById(1L);
        Mockito
                .doReturn(comment)
                .when(commentMapper).toComment(anyLong(), any(CommentDto.class));
        Mockito
                .doReturn(addComment)
                .when(commentMapper).toCommentDto(any(Comment.class));

        CommentDto postCommentDto = commentService.addComment(commentDto, bookId);
        assertThat(addComment, equalTo(postCommentDto));
        Mockito.verify(commentRepository, Mockito.times(1)).save(comment);
    }

    @Test
    void addComment_whenBookNotFound_thenThrowException() {
        final CommentDto commentDto = new CommentDto("Title", "Content");
        final long bookId = 1L;
        Mockito
                .doReturn(Optional.empty())
                .when(bookRepository).findById(bookId);

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> commentService.addComment(commentDto, bookId));
        assertThat(e.getMessage(), equalTo("404 NOT_FOUND \"Book Not Found\""));
        Mockito.verify(bookRepository, Mockito.times(1)).findById(bookId);
    }

    @Test
    void updateComment_whenNormal_thenReturnComment() {
        final Book book = new Book(1L, "Title", "Description", LocalDate.now());
        final Comment comment = new Comment(1, "Content");
        final Comment updateComment = new Comment(1, "Content");
        final CommentDto commentDto = new CommentDto("Title", "Content");
        final CommentDto addComment = new CommentDto("Title", "Content");
        comment.setBook(book);
        updateComment.setBook(book);
        Mockito
                .when(commentRepository.save(comment))
                .thenReturn(updateComment);
        Mockito
                .when(commentRepository.findById(comment.getId()))
                .thenReturn(Optional.of(comment));
        Mockito
                .doReturn(comment)
                .when(commentMapper).toComment(anyLong(), any(CommentDto.class));
        Mockito
                .doReturn(addComment)
                .when(commentMapper).toCommentDto(any(Comment.class));

        CommentDto updateCommentDto = commentService.updateComment(commentDto, comment.getId());
        assertThat(addComment, equalTo(updateCommentDto));
        Mockito.verify(commentRepository, Mockito.times(1))
                .save(comment);
    }

    @Test
    void updateComment_whenCommentNotFound_thenThrowException() {
        final CommentDto commentDto = new CommentDto("Title", "Content");

        final long commentId = 1L;
        Mockito
                .when(commentRepository.findById(commentId))
                .thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> commentService.updateComment(commentDto, commentId));
        assertThat(e.getMessage(), equalTo("404 NOT_FOUND \"Comment Not Found\""));
        Mockito.verify(commentRepository, Mockito.times(1)).findById(commentId);
    }

    @Test
    void deleteCommentById_whenNormal_thenDeleteComment() {
        final Comment comment = new Comment(1, "Content");
        final long commentId = 1L;
        Mockito
                .when(commentRepository.findById(comment.getId()))
                .thenReturn(Optional.of(comment));
        Mockito
                .doNothing()
                .when(commentRepository).deleteById(commentId);
        commentService.deleteComment(commentId);
        Mockito.verify(commentRepository, Mockito.times(1)).deleteById(commentId);
    }

    @Test
    void deleteCommentById_whenCommentNotFound_thenThrowException() {
        final long commentId = 1L;
        Mockito
                .doReturn(Optional.empty())
                .when(commentRepository).findById(commentId);
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> commentService.deleteComment(commentId));
        assertThat(e.getMessage(), equalTo("404 NOT_FOUND \"Comment Not Found\""));
        Mockito.verify(commentRepository, Mockito.times(1)).findById(commentId);
    }

    @Test
    void getCommentsByBook_whenNormal_thenReturnComments() {
        final Book book = new Book(1L, "Book", "Description", LocalDate.now());
        final long bookId = 1L;
        final Comment comment = new Comment(1, "Content");
        final CommentDto commentDto = new CommentDto("Book", "Content");
        final CommentDto commentDto1 = new CommentDto("Book", "Content");
        final Comment comment1 = new Comment(2, "Content");
        comment.setBook(book);
        comment1.setBook(book);
        Mockito
                .doReturn(Optional.of(book))
                .when(bookRepository).findById(1L);
        Mockito
                .doReturn(List.of(comment, comment1))
                .when(commentRepository).findCommentsByBook_Id(bookId);
        Mockito
                .doReturn(commentDto)
                .when(commentMapper).toCommentDto(comment);
        Mockito
                .doReturn(commentDto1)
                .when(commentMapper).toCommentDto(comment1);

        List<CommentDto> commentsDtoByBooks = commentService.getCommentsByBookId(bookId);
        assertThat(commentsDtoByBooks.size(), equalTo(2));
        Mockito.verify(commentRepository, Mockito.times(1)).findCommentsByBook_Id(bookId);
    }

    @Test
    void getCommentsByBookId_whenBookNotFound_thenThrowException() {
        final long bookId = 1L;
        Mockito
                .when(bookRepository.findById(bookId))
                .thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> commentService.getCommentsByBookId(bookId));
        assertThat(e.getMessage(), equalTo("404 NOT_FOUND \"Book Not Found\""));
        Mockito.verify(bookRepository, Mockito.times(1)).findById(bookId);
    }
}
