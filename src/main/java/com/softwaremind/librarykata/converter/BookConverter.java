package com.softwaremind.librarykata.converter;

import com.softwaremind.librarykata.controller.dto.BookDto;
import com.softwaremind.librarykata.controller.requests.CreateBookRequest;
import com.softwaremind.librarykata.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookConverter {
    Book toEntity(CreateBookRequest createBookRequest);

    BookDto toDto(Book book);
}
