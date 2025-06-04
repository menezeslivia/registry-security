package com.seguranca_urbana.backend.infra;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<RestExceptionError> handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request) {
    RestExceptionError error = new RestExceptionError(
        HttpStatus.BAD_REQUEST.value(),
        "Bad Request",
        e.getMessage(),
        request.getRequestURI(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
}

@ExceptionHandler(Exception.class)
public ResponseEntity<RestExceptionError> handleGenericException(Exception e, HttpServletRequest request) {
    RestExceptionError error = new RestExceptionError(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Internal Server Error",
        "Ocorreu um erro inesperado. Tente novamente mais tarde.",
        request.getRequestURI(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
}
}

