package org.example.marketplace.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.example.marketplace.exception.EntityConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void onRuntimeException() {
        RuntimeException ex = new RuntimeException("Something went wrong");

        ResponseEntity<String> response = handler.onRuntimeException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Something went wrong", response.getBody());
    }

    @Test
    void onConstraintViolationException() {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);

        Path path = mock(Path.class);
        when(path.toString()).thenReturn("fieldName");

        when(violation.getInvalidValue()).thenReturn("bad_value");
        when(violation.getPropertyPath()).thenReturn(path);
        when(violation.getMessage()).thenReturn("must not be null");

        Set<ConstraintViolation<?>> violations = Set.of(violation);

        ConstraintViolationException ex =
                new ConstraintViolationException("Validation failed", violations);

        ResponseEntity<String> response = handler.onConstraintViolationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("fieldName"));
    }

    @Test
    void onEntityNotFoundException() {
        EntityNotFoundException ex = new EntityNotFoundException("Not found");

        ResponseEntity<String> response = handler.onEntityNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not found", response.getBody());
    }

    @Test
    void onEmailConflictException() {
        EntityConflictException ex = new EntityConflictException("Email already exists");

        ResponseEntity<String> response = handler.onEmailConflictException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email already exists", response.getBody());
    }
}