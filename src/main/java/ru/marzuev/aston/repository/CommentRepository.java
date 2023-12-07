package ru.marzuev.aston.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.marzuev.aston.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
