package ru.marzuev.aston.model.mapper;

import org.mapstruct.Mapper;
import ru.marzuev.aston.model.Author;
import ru.marzuev.aston.model.dto.AuthorDto;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDto toAuthorDto(Author author);
    Author toAuthor(long authorId, AuthorDto authorDto);
}
