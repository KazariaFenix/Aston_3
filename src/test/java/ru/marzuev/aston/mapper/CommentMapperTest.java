package ru.marzuev.aston.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.marzuev.aston.model.Comment;
import ru.marzuev.aston.model.dto.CommentDto;
import ru.marzuev.aston.model.mapper.CommentMapper;
import ru.marzuev.aston.model.mapper.CommentMapperImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentMapperTest {
    private Comment comment;
    private CommentDto commentDto;
    private CommentMapper commentMapper;

    @BeforeEach
    void setUp() {
        comment = new Comment(1L, "Content");
        commentDto = new CommentDto(null, "Content");
        commentMapper = new CommentMapperImpl();
    }

    @Test
    void mappingToComment_whenNormal_thenReturnComment() {
        Comment mapping = commentMapper.toComment(1L, commentDto);

        assertEquals(comment.getContent(), mapping.getContent());
        assertEquals(comment.getId(), mapping.getId());
        assertEquals(comment.getBook(), mapping.getBook());
    }

    @Test
    void mappingToCommentDto_whenNormal_thenReturnCommentDto() {
        CommentDto mapping = commentMapper.toCommentDto(comment);

        assertEquals(commentDto.getContent(), mapping.getContent());
        assertEquals(commentDto.getTitleBook(), mapping.getTitleBook());
    }
}
