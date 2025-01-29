package com.softwaremind.librarykata.converter;

import com.softwaremind.librarykata.controller.dto.BorrowedBookDto;
import com.softwaremind.librarykata.model.BorrowedBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BorrowedBookConverter {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "bookId", source = "book.id")
    BorrowedBookDto toDto(BorrowedBook book);
}
