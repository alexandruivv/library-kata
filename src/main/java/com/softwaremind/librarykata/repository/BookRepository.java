package com.softwaremind.librarykata.repository;

import com.softwaremind.librarykata.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    @Query("""
        SELECT b from Book b
        WHERE b.id not in (
            SELECT bb.book.id from BorrowedBook bb
        )
    """)
    List<Book> findNotBorrowedBooks();
}
