package ru.marzuev.aston.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.marzuev.aston.model.Author;
import ru.marzuev.aston.model.Book;

import java.util.List;
import java.util.Map;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query(value = "SELECT * FROM authors AS a LEFT JOIN authors_books AS ab " +
            "ON a.author_id = ab.author_id LEFT JOIN books AS b ON ab.book_id = b.book_id ORDER BY a.author_id",
            nativeQuery = true)
    Map<Author, List<Book>> getAuthorsWithBooks();
    @Query(value = "SELECT * FROM authors WHERE author_id IN " +
            "(SELECT ab.author_id FROM authors_books AS ab WHERE ab.book_id = ?)", nativeQuery = true)
    List<Author> findAuthorByBookId(long bookId);
}
