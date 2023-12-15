package ru.marzuev.aston.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.marzuev.aston.config.PersistenceConfigForTest;
import ru.marzuev.aston.model.Author;
import ru.marzuev.aston.model.Book;
import ru.marzuev.aston.model.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.marzuev.aston.config.PersistenceConfigForTest.postgres;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersistenceConfigForTest.class, AuthorRepository.class, BookRepository.class,
        CommentRepository.class})
class CommentRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;
    private Author author;
    private Book book;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void initAuthor() {
        author = new Author(0L, "Bob", LocalDate.of(1990, 10, 25));
        author.setListBooks(new ArrayList<>());
        author = authorRepository.save(author);
        book = new Book(0, "Title", "Description", LocalDate.now());
        book.setListAuthors(List.of(author));
        book = bookRepository.save(book);
    }

    @AfterEach
    void afterEach() {
        authorRepository.deleteAll();
        bookRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    void saveComment_whenNormal_thenReturnComment() {
        Comment comment = new Comment(0, "Content");
        comment.setBook(book);

        Comment saveComment = commentRepository.save(comment);

        assertEquals(comment.getBook(), saveComment.getBook());
        assertEquals(comment.getContent(), saveComment.getContent());
    }

    @Test
    void updateComment_whenNormal_thenReturnComment() {
        Comment comment = new Comment(0, "Content");
        Comment newComment = new Comment(0, "UPDATE");

        comment.setBook(book);
        newComment.setBook(book);

        Comment saveComment = commentRepository.save(comment);

        newComment.setId(saveComment.getId());

        Comment update = commentRepository.save(newComment);

        assertEquals(update.getId(), newComment.getId());
    }

    @Test
    void deleteComment_whenNormal_thenDeleteComment() {
        Comment comment = new Comment(0, "Content");
        Comment newComment = new Comment(0, "UPDATE");

        comment.setBook(book);
        newComment.setBook(book);

        Comment saveComment = commentRepository.save(comment);

        commentRepository.save(newComment);
        commentRepository.deleteById(saveComment.getId());

        List<Comment> comments = commentRepository.findCommentsByBook_Id(book.getId());

        assertEquals(1, comments.size());
    }

    @Test
    void findCommentsByBookId_whenNormal_thenReturnComments() {
        Comment comment = new Comment(0, "Content");
        Comment newComment = new Comment(0, "UPDATE");

        comment.setBook(book);
        newComment.setBook(book);

        commentRepository.save(comment);
        commentRepository.save(newComment);
        List<Comment> comments = commentRepository.findCommentsByBook_Id(book.getId());

        assertEquals(2, comments.size());
    }

}
