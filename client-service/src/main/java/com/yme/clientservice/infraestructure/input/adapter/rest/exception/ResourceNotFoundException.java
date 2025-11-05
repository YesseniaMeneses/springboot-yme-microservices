package com.yme.clientservice.infraestructure.input.adapter.rest.exception;

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
