package ru.marzuev.aston.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.marzuev.aston.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
