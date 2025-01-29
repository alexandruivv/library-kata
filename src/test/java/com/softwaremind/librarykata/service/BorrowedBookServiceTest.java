package com.softwaremind.librarykata.service;

import com.softwaremind.librarykata.model.Book;
import com.softwaremind.librarykata.model.BorrowedBook;
import com.softwaremind.librarykata.repository.BorrowedBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowedBookServiceTest {

    @Mock
    private BorrowedBookRepository borrowedBookRepository;

    @InjectMocks
    private BorrowedBookService borrowedBookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByUserIdAndBookId_ShouldReturnBorrowedBook_WhenExists() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setId(UUID.randomUUID());
        when(borrowedBookRepository.findByBookIdAndUserId(bookId, userId)).thenReturn(Optional.of(borrowedBook));

        // Act
        Optional<BorrowedBook> result = borrowedBookService.findByUserIdAndBookId(userId, bookId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(borrowedBook, result.get());
        verify(borrowedBookRepository, times(1)).findByBookIdAndUserId(bookId, userId);
    }

    @Test
    void findByUserIdAndBookId_ShouldReturnEmptyOptional_WhenNotExists() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        when(borrowedBookRepository.findByBookIdAndUserId(bookId, userId)).thenReturn(Optional.empty());

        // Act
        Optional<BorrowedBook> result = borrowedBookService.findByUserIdAndBookId(userId, bookId);

        // Assert
        assertFalse(result.isPresent());
        verify(borrowedBookRepository, times(1)).findByBookIdAndUserId(bookId, userId);
    }

    @Test
    void findBorrowedBooksByUserId_ShouldReturnListOfBooks() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Book book1 = new Book();
        Book book2 = new Book();
        when(borrowedBookRepository.findBorrowedBooksByUserId(userId)).thenReturn(List.of(book1, book2));

        // Act
        List<Book> result = borrowedBookService.findBorrowedBooksByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(book1));
        assertTrue(result.contains(book2));
        verify(borrowedBookRepository, times(1)).findBorrowedBooksByUserId(userId);
    }

    @Test
    void save_ShouldSaveBorrowedBook() {
        // Arrange
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setId(UUID.randomUUID());
        when(borrowedBookRepository.save(borrowedBook)).thenReturn(borrowedBook);

        // Act
        BorrowedBook savedBorrowedBook = borrowedBookService.save(borrowedBook);

        // Assert
        assertNotNull(savedBorrowedBook);
        assertEquals(borrowedBook, savedBorrowedBook);
        verify(borrowedBookRepository, times(1)).save(borrowedBook);
    }

    @Test
    void findById_ShouldReturnBorrowedBook_WhenExists() {
        // Arrange
        UUID id = UUID.randomUUID();
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setId(id);
        when(borrowedBookRepository.findById(id)).thenReturn(Optional.of(borrowedBook));

        // Act
        Optional<BorrowedBook> result = borrowedBookService.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(borrowedBook, result.get());
        verify(borrowedBookRepository, times(1)).findById(id);
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenNotExists() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(borrowedBookRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<BorrowedBook> result = borrowedBookService.findById(id);

        // Assert
        assertFalse(result.isPresent());
        verify(borrowedBookRepository, times(1)).findById(id);
    }

    @Test
    void delete_ShouldDeleteBorrowedBookById() {
        // Arrange
        UUID id = UUID.randomUUID();
        doNothing().when(borrowedBookRepository).deleteById(id);

        // Act
        borrowedBookService.delete(id);

        // Assert
        verify(borrowedBookRepository, times(1)).deleteById(id);
    }
}
