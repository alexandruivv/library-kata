package com.softwaremind.librarykata.service;

import com.softwaremind.librarykata.model.Book;
import com.softwaremind.librarykata.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class BookService extends BaseCrudService<Book> {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Book> findNotBorrowedBooks() {
        return repository.findNotBorrowedBooks();
    }
}
