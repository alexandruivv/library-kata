package com.softwaremind.librarykata.exception;

import lombok.Getter;

@Getter
public enum ErrorCodes {
    BOOK_ALREADY_BORROWED("001", "Book is already borrowed."),
    ENTITY_NOT_FOUND("002", "Entity was not found."),
    INTERNAL_SERVER_ERROR("003", "Internal server error. Please contact the administrator"),
    INVALID_CREDENTIALS("004", "Invalid credentials");

    private final String code;
    private final String message;

    ErrorCodes(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
