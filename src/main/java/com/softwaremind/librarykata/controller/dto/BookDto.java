package com.softwaremind.librarykata.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private UUID id;
    private String title;
    private String description;
    private String author;
    private LocalDate publicationDate;
}
