package com.softwaremind.librarykata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwaremind.librarykata.controller.dto.BookDto;
import com.softwaremind.librarykata.controller.dto.BorrowedBookDto;
import com.softwaremind.librarykata.controller.requests.CreateBookRequest;
import com.softwaremind.librarykata.facade.BookFacade;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookFacade bookFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllBooks() throws Exception {
        // Arrange
        List<BookDto> books = List.of(
                new BookDto(UUID.randomUUID(), "Book 1", "A great book", "Author 1", LocalDate.of(2021, 5, 20)),
                new BookDto(UUID.randomUUID(), "Book 2", "Another great book", "Author 2", LocalDate.of(2019, 8, 10))
        );
        when(bookFacade.getBooks()).thenReturn(books);

        // Act & Assert
        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[0].description").value("A great book"))
                .andExpect(jsonPath("$[1].title").value("Book 2"));

        verify(bookFacade).getBooks();
    }

    @Test
    void shouldCreateBook() throws Exception {
        // Arrange
        CreateBookRequest createBookRequest = new CreateBookRequest(
                "New Book", "A new description", "New Author", LocalDate.of(2023, 1, 15)
        );
        BookDto bookDto = new BookDto(UUID.randomUUID(), "New Book", "A new description", "New Author", LocalDate.of(2023, 1, 15));

        when(bookFacade.createBook(createBookRequest)).thenReturn(bookDto);

        // Act & Assert
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Book"))
                .andExpect(jsonPath("$.description").value("A new description"))
                .andExpect(jsonPath("$.author").value("New Author"));

        verify(bookFacade).createBook(createBookRequest);
    }

    @Test
    void shouldBorrowBook() throws Exception {
        // Arrange
        UUID bookId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        BorrowedBookDto borrowedBookDto = new BorrowedBookDto(
                userId, bookId, LocalDate.of(2023, 2, 10), LocalDate.of(2023, 2, 20)
        );

        when(bookFacade.borrowBook(bookId, userId)).thenReturn(borrowedBookDto);

        // Act & Assert
        mockMvc.perform(post("/books/{bookId}/borrow", bookId)
                        .header("User-Id", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.bookId").value(bookId.toString()))
                .andExpect(jsonPath("$.rentalDate").value("2023-02-10"))
                .andExpect(jsonPath("$.returnDate").value("2023-02-20"));

        verify(bookFacade).borrowBook(bookId, userId);
    }

    @Test
    void shouldReturnBook() throws Exception {
        // Arrange
        UUID bookId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        doNothing().when(bookFacade).returnBook(bookId, userId);

        // Act & Assert
        mockMvc.perform(post("/books/{bookId}/return", bookId)
                        .header("User-Id", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(bookFacade).returnBook(bookId, userId);
    }

    @Test
    void shouldGetBorrowedBooks() throws Exception {
        // Arrange
        UUID userId = UUID.randomUUID();
        List<BookDto> borrowedBooks = List.of(
                new BookDto(UUID.randomUUID(), "Borrowed Book 1", "A borrowed book", "Author 1", LocalDate.of(2020, 3, 10))
        );

        when(bookFacade.getBorrowedBooks(userId)).thenReturn(borrowedBooks);

        // Act & Assert
        mockMvc.perform(get("/books/borrowed")
                        .header("User-Id", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Borrowed Book 1"))
                .andExpect(jsonPath("$[0].description").value("A borrowed book"));

        verify(bookFacade).getBorrowedBooks(userId);
    }

    @Test
    void shouldGetNotBorrowedBooks() throws Exception {
        // Arrange
        List<BookDto> notBorrowedBooks = List.of(
                new BookDto(UUID.randomUUID(), "Available Book", "An available book", "Author 2", LocalDate.of(2018, 6, 5))
        );

        when(bookFacade.getNotBorrowedBooks()).thenReturn(notBorrowedBooks);

        // Act & Assert
        mockMvc.perform(get("/books/not-borrowed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Available Book"))
                .andExpect(jsonPath("$[0].description").value("An available book"));

        verify(bookFacade).getNotBorrowedBooks();
    }
}
