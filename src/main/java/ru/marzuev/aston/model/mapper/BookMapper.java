package ru.marzuev.aston.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.marzuev.aston.model.Book;
import ru.marzuev.aston.model.dto.BookDto;

@Mapper(componentModel = "spring", uses = {CommentMapper.class, AuthorMapper.class})
public interface BookMapper {

    //@Mapping(source = "listComments", target = "book.listComments")
    BookDto toBookDto(Book book);

    @Mapping(target = "listAuthors", source = "bookDto.listAuthors")
    Book toBook(BookDto bookDto);
}
