package com.softwaremind.librarykata.controller;

import com.softwaremind.librarykata.controller.dto.BookDto;
import com.softwaremind.librarykata.controller.dto.BorrowedBookDto;
import com.softwaremind.librarykata.controller.requests.CreateBookRequest;
import com.softwaremind.librarykata.facade.BookFacade;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookFacade bookFacade;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookFacade.getBooks());
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody CreateBookRequest createBookRequest) {
        BookDto createdBook = bookFacade.createBook(createBookRequest);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PostMapping("/{bookId}/borrow")
    public ResponseEntity<BorrowedBookDto> borrowBook(@PathVariable UUID bookId, @RequestHeader(name = "User-Id") UUID userId) {
        BorrowedBookDto borrowedBookDto = bookFacade.borrowBook(bookId, userId);
        return ResponseEntity.ok(borrowedBookDto);
    }

    @PostMapping("/{bookId}/return")
    public ResponseEntity<Void> returnBook(@PathVariable UUID bookId, @RequestHeader(name = "User-Id") UUID userId) {
        bookFacade.returnBook(bookId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/borrowed")
    public ResponseEntity<List<BookDto>> getBorrowedBooks(@RequestHeader(name = "User-Id") UUID userId) {
        List<BookDto> borrowedBooks = bookFacade.getBorrowedBooks(userId);
        return ResponseEntity.ok(borrowedBooks);
    }

    @GetMapping("/not-borrowed")
    public ResponseEntity<List<BookDto>> getNotBorrowedBooks() {
        List<BookDto> notBorrowedBooks = bookFacade.getNotBorrowedBooks();
        return ResponseEntity.ok(notBorrowedBooks);
    }
}
