package ru.marzuev.aston.model.mapper;

import org.mapstruct.Mapper;
import ru.marzuev.aston.model.Book;
import ru.marzuev.aston.model.dto.BookTitle;

@Mapper(componentModel = "spring")
public interface BookTitleMapper {
    BookTitle toBookTitle(Book book);
}
