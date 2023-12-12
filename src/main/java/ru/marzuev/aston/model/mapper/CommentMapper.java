package ru.marzuev.aston.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.marzuev.aston.model.Comment;
import ru.marzuev.aston.model.dto.CommentDto;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "titleBook", source = "comment.book.title")
    CommentDto toCommentDto(Comment comment);
    @Mapping(target = "id", source = "commentId")
    Comment toComment(long commentId, CommentDto commentDto);
}
