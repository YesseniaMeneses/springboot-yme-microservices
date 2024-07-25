package com.yme.movementsservice.exception;

/**
 * Exceptions for values not found.
 */
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException() {
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
