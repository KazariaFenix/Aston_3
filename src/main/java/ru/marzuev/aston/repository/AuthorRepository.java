package ru.marzuev.aston.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marzuev.aston.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
