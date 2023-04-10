package ru.practicum.ewm.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.rmi.AccessException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({AccessException.class, DataIntegrityViolationException.class})
    private ResponseEntity<Exception> handleException(DataIntegrityViolationException e) {
        Exception ex = Exception.builder()
                .message(e.getMessage())
                .reason("CONFLICT")
                .status(String.valueOf(HttpStatus.CONFLICT))
                .timestamp(LocalDateTime.now().toString())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex);
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<Exception> handleException(NotFoundException e) {
        Exception ex = Exception.builder()
                .message(e.getMessage())
                .reason("NOT_FOUND")
                .status(HttpStatus.NOT_FOUND.toString())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<Exception> handleException(ConstraintViolationException e) {
        Exception ex = Exception.builder()
                .message(e.getMessage())
                .reason("BAD_REQUEST")
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
    }
}