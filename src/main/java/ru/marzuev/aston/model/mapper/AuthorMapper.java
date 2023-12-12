package ru.marzuev.aston.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.marzuev.aston.model.Author;
import ru.marzuev.aston.model.dto.AuthorDto;

@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface AuthorMapper {

    @Mapping(target = "booksList", source = "author.listBooks")
    AuthorDto toAuthorDto(Author author);

    Author toAuthor(AuthorDto authorDto);

}
