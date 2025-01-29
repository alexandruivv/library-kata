package com.softwaremind.librarykata.service;

import com.softwaremind.librarykata.model.Book;
import com.softwaremind.librarykata.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ShouldSaveBook() {
        // Arrange
        Book book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle("Test Book");
        when(bookRepository.save(book)).thenReturn(book);

        // Act
        Book savedBook = bookService.save(book);

        // Assert
        assertNotNull(savedBook);
        assertEquals(book, savedBook);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void findById_ShouldReturnBook_WhenIdExists() {
        // Arrange
        UUID id = UUID.randomUUID();
        Book book = new Book();
        book.setId(id);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        // Act
        Optional<Book> result = bookService.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(book, result.get());
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenIdDoesNotExist() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Book> result = bookService.findById(id);

        // Assert
        assertFalse(result.isPresent());
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    void findAll_ShouldReturnListOfBooks() {
        // Arrange
        Book book1 = new Book();
        Book book2 = new Book();
        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        // Act
        List<Book> books = bookService.findAll();

        // Assert
        assertNotNull(books);
        assertEquals(2, books.size());
        assertTrue(books.contains(book1));
        assertTrue(books.contains(book2));
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void delete_ShouldDeleteBookById() {
        // Arrange
        UUID id = UUID.randomUUID();
        doNothing().when(bookRepository).deleteById(id);

        // Act
        bookService.delete(id);

        // Assert
        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);
        verify(bookRepository, times(1)).deleteById(captor.capture());
        assertEquals(id, captor.getValue());
    }
}
