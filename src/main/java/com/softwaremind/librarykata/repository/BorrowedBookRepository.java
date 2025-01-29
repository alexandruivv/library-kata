package com.softwaremind.librarykata.repository;

import com.softwaremind.librarykata.model.Book;
import com.softwaremind.librarykata.model.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, UUID> {
    Optional<BorrowedBook> findByBookIdAndUserId(UUID bookId, UUID userId);

    @Query("""
        SELECT bb.book from BorrowedBook bb
        WHERE bb.user.id = :userId
    """)
    List<Book> findBorrowedBooksByUserId(UUID userId);
}
