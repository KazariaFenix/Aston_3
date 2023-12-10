package ru.marzuev.aston.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.marzuev.aston.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByBook_Id(long bookId);

    @Query(value = "SELECT * FROM comments WHERE book_id " +
            "IN (SELECT ab.book_id FROM authors_books AS ab WHERE ab.author_id = ?)", nativeQuery = true)
    List<Comment> findCommentByBookByAuthorId(long authorId);
}
