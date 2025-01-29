package com.softwaremind.librarykata.controller.exception;

import com.softwaremind.librarykata.exception.ErrorCodes;
import com.softwaremind.librarykata.exception.LibraryKataResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestResponseExceptionHandler {
    @ExceptionHandler(LibraryKataResponseException.class)
    public ResponseEntity<ErrorResponse> handleLibraryKataResponseException(LibraryKataResponseException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getCode(), e.getErrorCode().getMessage()), e.getHttpStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleLibraryKataResponseException(RuntimeException e) {
        log.error("Error occurred", e);
        return new ResponseEntity<>(new ErrorResponse(ErrorCodes.INTERNAL_SERVER_ERROR.getCode(), ErrorCodes.INTERNAL_SERVER_ERROR.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
