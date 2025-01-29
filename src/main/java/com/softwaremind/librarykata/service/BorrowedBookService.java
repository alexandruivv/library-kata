package com.softwaremind.librarykata.service;

import com.softwaremind.librarykata.model.Book;
import com.softwaremind.librarykata.model.BorrowedBook;
import com.softwaremind.librarykata.repository.BorrowedBookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
public class BorrowedBookService extends BaseCrudService<BorrowedBook> {
    private final BorrowedBookRepository repository;

    public BorrowedBookService(BorrowedBookRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Optional<BorrowedBook> findByUserIdAndBookId(UUID userId, UUID bookId) {
        return repository.findByBookIdAndUserId(bookId, userId);
    }

    public List<Book> findBorrowedBooksByUserId(UUID userId) {
        return repository.findBorrowedBooksByUserId(userId);
    }
}
