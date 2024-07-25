package com.yme.clientservice.exception;

/**
 * Exceptions for duplicated values.
 */
public class ResourceAlreadyExistsException extends RuntimeException{
    public ResourceAlreadyExistsException() {
    }
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
