package com.softwaremind.librarykata.facade;

import com.softwaremind.librarykata.config.ApplicationConfigParams;
import com.softwaremind.librarykata.controller.dto.BookDto;
import com.softwaremind.librarykata.controller.dto.BorrowedBookDto;
import com.softwaremind.librarykata.controller.requests.CreateBookRequest;
import com.softwaremind.librarykata.converter.BookConverter;
import com.softwaremind.librarykata.converter.BorrowedBookConverter;
import com.softwaremind.librarykata.exception.ErrorCodes;
import com.softwaremind.librarykata.exception.LibraryKataResponseException;
import com.softwaremind.librarykata.model.Book;
import com.softwaremind.librarykata.model.BorrowedBook;
import com.softwaremind.librarykata.model.User;
import com.softwaremind.librarykata.repository.BorrowedBookRepository;
import com.softwaremind.librarykata.service.BaseCrudService;
import com.softwaremind.librarykata.service.BookService;
import com.softwaremind.librarykata.service.BorrowedBookService;
import com.softwaremind.librarykata.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BookFacade {
    private final BookService bookService;
    private final UserService userService;
    private final BookConverter bookConverter;
    private final BorrowedBookService borrowedBookService;
    private final ApplicationConfigParams applicationConfigParams;
    private final BorrowedBookConverter borrowedBookConverter;

    public List<BookDto> getBooks() {
        return bookService.findAll().stream().map(bookConverter::toDto).toList();
    }

    public BookDto createBook(CreateBookRequest createBookRequest) {
        Book bookToBeSaved = bookConverter.toEntity(createBookRequest);
        Book savedBook = bookService.save(bookToBeSaved);
        return bookConverter.toDto(savedBook);
    }

    public BorrowedBookDto borrowBook(UUID bookId, UUID userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new LibraryKataResponseException(HttpStatus.NOT_FOUND, ErrorCodes.ENTITY_NOT_FOUND));
        Book book = bookService.findById(bookId)
                .orElseThrow(() -> new LibraryKataResponseException(HttpStatus.NOT_FOUND, ErrorCodes.ENTITY_NOT_FOUND));
        Optional<BorrowedBook> borrowedBook = borrowedBookService.findByUserIdAndBookId(userId, bookId);
        if (borrowedBook.isPresent()) {
            throw new LibraryKataResponseException(HttpStatus.CONFLICT, ErrorCodes.BOOK_ALREADY_BORROWED);
        }
        BorrowedBook newBorrowedBook =
                borrowedBookService.save(new BorrowedBook(user, book, LocalDate.now(), LocalDate.now().plusDays(applicationConfigParams.getDaysOfRental())));
        return borrowedBookConverter.toDto(newBorrowedBook);
    }

    public void returnBook(UUID bookId, UUID userId) {
        BorrowedBook borrowedBook = borrowedBookService.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new LibraryKataResponseException(HttpStatus.NOT_FOUND, ErrorCodes.ENTITY_NOT_FOUND));
        borrowedBookService.delete(borrowedBook.getId());
    }

    public List<BookDto> getBorrowedBooks(UUID userId) {
        List<Book> borrowedBooksByUser = borrowedBookService.findBorrowedBooksByUserId(userId);
        if (CollectionUtils.isEmpty(borrowedBooksByUser)) {
            return List.of();
        }
        return borrowedBooksByUser.stream().map(bookConverter::toDto).toList();
    }

    public List<BookDto> getNotBorrowedBooks() {
        return bookService.findNotBorrowedBooks().stream().map(bookConverter::toDto).toList();
    }
}
