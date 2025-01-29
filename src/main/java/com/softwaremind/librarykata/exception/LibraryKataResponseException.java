package com.softwaremind.librarykata.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LibraryKataResponseException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final ErrorCodes errorCode;

    public LibraryKataResponseException(HttpStatus httpStatus, ErrorCodes errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public LibraryKataResponseException(HttpStatus httpStatus, ErrorCodes errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}
