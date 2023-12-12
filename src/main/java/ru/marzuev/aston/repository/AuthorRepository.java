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
}
