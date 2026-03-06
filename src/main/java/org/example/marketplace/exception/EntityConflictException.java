package org.example.marketplace.exception;

public class EntityConflictException extends IllegalArgumentException {

    public EntityConflictException(String message) {
        super(message);
    }
}
