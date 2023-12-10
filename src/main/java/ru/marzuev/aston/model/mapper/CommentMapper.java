package ru.marzuev.aston.model.mapper;

import org.mapstruct.Mapper;
import ru.marzuev.aston.model.Comment;
import ru.marzuev.aston.model.dto.CommentDto;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto toCommentDto(Comment comment);
    Comment toComment(long commentId, CommentDto commentDto);
}
