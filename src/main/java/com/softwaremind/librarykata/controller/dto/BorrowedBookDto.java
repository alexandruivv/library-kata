package com.softwaremind.librarykata.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowedBookDto {
    private UUID userId;
    private UUID bookId;
    private LocalDate rentalDate;
    private LocalDate returnDate;
}
