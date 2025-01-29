package com.softwaremind.librarykata.facade;

import com.softwaremind.librarykata.config.ApplicationConfigParams;
import com.softwaremind.librarykata.controller.dto.BookDto;
import com.softwaremind.librarykata.controller.dto.BorrowedBookDto;
import com.softwaremind.librarykata.controller.requests.CreateBookRequest;
import com.softwaremind.librarykata.converter.BookConverter;
import com.softwaremind.librarykata.converter.BorrowedBookConverter;
import com.softwaremind.librarykata.exception.LibraryKataResponseException;
import com.softwaremind.librarykata.model.Book;
import com.softwaremind.librarykata.model.BorrowedBook;
import com.softwaremind.librarykata.model.User;
import com.softwaremind.librarykata.service.BaseCrudService;
import com.softwaremind.librarykata.service.BookService;
import com.softwaremind.librarykata.service.BorrowedBookService;
import com.softwaremind.librarykata.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookFacadeTest {

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @Mock
    private BookConverter bookConverter;

    @Mock
    private BorrowedBookService borrowedBookService;

    @Mock
    private ApplicationConfigParams applicationConfigParams;

    @Mock
    private BorrowedBookConverter borrowedBookConverter;

    @InjectMocks
    private BookFacade bookFacade;

    @Test
    void createBook_ShouldReturnBookDto_WhenBookIsCreated() {
        // Arrange
        CreateBookRequest createBookRequest = new CreateBookRequest();
        Book book = new Book();
        Book savedBook = new Book();
        BookDto bookDto = new BookDto();

        when(bookConverter.toEntity(createBookRequest)).thenReturn(book);
        when(bookService.save(book)).thenReturn(savedBook);
        when(bookConverter.toDto(savedBook)).thenReturn(bookDto);

        // Act
        BookDto result = bookFacade.createBook(createBookRequest);

        // Assert
        assertNotNull(result);
        assertEquals(bookDto, result);
        verify(bookConverter, times(1)).toEntity(createBookRequest);
        verify(bookService, times(1)).save(book);
        verify(bookConverter, times(1)).toDto(savedBook);
    }

    @Test
    void borrowBook_ShouldReturnBorrowedBookDto_WhenBookIsBorrowed() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        User user = new User();
        Book book = new Book();
        BorrowedBook borrowedBook = new BorrowedBook();
        BorrowedBookDto borrowedBookDto = new BorrowedBookDto();

        when(userService.findById(userId)).thenReturn(Optional.of(user));
        when(bookService.findById(bookId)).thenReturn(Optional.of(book));
        when(borrowedBookService.findByUserIdAndBookId(userId, bookId)).thenReturn(Optional.empty());
        when(applicationConfigParams.getDaysOfRental()).thenReturn(7);
        when(borrowedBookService.save(any())).thenReturn(borrowedBook);
        when(borrowedBookConverter.toDto(borrowedBook)).thenReturn(borrowedBookDto);

        // Act
        BorrowedBookDto result = bookFacade.borrowBook(bookId, userId);

        // Assert
        assertNotNull(result);
        assertEquals(borrowedBookDto, result);
        verify(userService, times(1)).findById(userId);
        verify(bookService, times(1)).findById(bookId);
        verify(borrowedBookService, times(1)).findByUserIdAndBookId(userId, bookId);
        verify(borrowedBookService, times(1)).save(any());
        verify(borrowedBookConverter, times(1)).toDto(borrowedBook);
    }

    @Test
    void borrowBook_ShouldThrowException_WhenBookIsAlreadyBorrowed() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        User user = new User();
        Book book = new Book();
        BorrowedBook borrowedBook = new BorrowedBook();

        when(userService.findById(any())).thenReturn(Optional.of(user));
        when(bookService.findById(any())).thenReturn(Optional.of(book));
        when(borrowedBookService.findByUserIdAndBookId(any(), any())).thenReturn(Optional.of(borrowedBook));

        // Act & Assert
        LibraryKataResponseException exception = assertThrows(
                LibraryKataResponseException.class,
                () -> bookFacade.borrowBook(bookId, userId)
        );

        // Assert
        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
        verify(borrowedBookService, never()).save(any());
    }

    @Test
    void returnBook_ShouldDeleteBorrowedBook_WhenBookIsReturned() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setId(UUID.randomUUID());

        when(borrowedBookService.findByUserIdAndBookId(userId, bookId)).thenReturn(Optional.of(borrowedBook));
        doNothing().when(borrowedBookService).delete(borrowedBook.getId());

        // Act
        bookFacade.returnBook(bookId, userId);

        // Assert
        verify(borrowedBookService, times(1)).findByUserIdAndBookId(userId, bookId);
        verify(borrowedBookService, times(1)).delete(borrowedBook.getId());
    }

    @Test
    void returnBook_ShouldThrowException_WhenBorrowedBookNotFound() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();

        when(borrowedBookService.findByUserIdAndBookId(userId, bookId)).thenReturn(Optional.empty());

        // Act & Assert
        LibraryKataResponseException exception = assertThrows(
                LibraryKataResponseException.class,
                () -> bookFacade.returnBook(bookId, userId)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(borrowedBookService, never()).delete(any());
    }

    @Test
    void getBorrowedBooks_ShouldReturnListOfBookDtos_WhenBorrowedBooksExist() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Book book1 = new Book();
        book1.setId(UUID.randomUUID());
        Book book2 = new Book();
        book2.setId(UUID.randomUUID());
        BookDto bookDto1 = new BookDto();
        BookDto bookDto2 = new BookDto();

        when(borrowedBookService.findBorrowedBooksByUserId(userId)).thenReturn(List.of(book1, book2));
        when(bookConverter.toDto(book1)).thenReturn(bookDto1);
        when(bookConverter.toDto(book2)).thenReturn(bookDto2);

        // Act
        List<BookDto> result = bookFacade.getBorrowedBooks(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(bookDto1));
        assertTrue(result.contains(bookDto2));
        verify(borrowedBookService, times(1)).findBorrowedBooksByUserId(userId);
        verify(bookConverter, times(1)).toDto(book1);
        verify(bookConverter, times(1)).toDto(book2);
    }
}
