package ru.marzuev.aston.model.mapper;

import org.mapstruct.Mapper;
import ru.marzuev.aston.model.Book;
import ru.marzuev.aston.model.dto.BookDto;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toBookDto(Book book);
    Book toBook(long bookId, BookDto bookDto);
}
