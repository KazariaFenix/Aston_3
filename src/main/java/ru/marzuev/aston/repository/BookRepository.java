package ru.marzuev.aston.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.marzuev.aston.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT * FROM books " +
            "WHERE id IN (SELECT ab.book_id FROM authors_books AS ab WHERE ab.author_id = ?)", nativeQuery = true)
    List<Book> findBooksByAuthorId(long authorId);

    @Query(value = "DELETE FROM books WHERE id = ?", nativeQuery = true)
    void deleteBooksById(long bookId);
}
