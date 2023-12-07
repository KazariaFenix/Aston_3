package ru.marzuev.aston.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.marzuev.aston.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
