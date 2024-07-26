package com.yme.clientservice.exception;

import com.yme.clientservice.constant.ErrorMessages;
import com.yme.clientservice.domain.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to manage all custom exceptions.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value= ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundException(ResourceNotFoundException ex) {
        log.error("{}: {}", HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(value= ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> resourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        log.error("{}: {}", HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage()));
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> dataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("{}: {}", HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT.value(), ErrorMessages.ERROR_DATA_INTEGRITY_VIOLATION));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constraintViolationException(ConstraintViolationException ex) {
        log.error("{}: {}", HttpStatus.BAD_REQUEST, ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach((error) -> {
            String fieldName = error.getPropertyPath().toString();
            String errorMessage = error.getMessageTemplate();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errors.toString()));
    }
}
