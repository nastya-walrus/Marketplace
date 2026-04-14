package org.example.marketplace.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.marketplace.exception.EntityConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void handleValidationException() {
        MethodArgumentNotValidException ex =
                org.mockito.Mockito.mock(MethodArgumentNotValidException.class);

        org.springframework.validation.BindingResult bindingResult =
                org.mockito.Mockito.mock(org.springframework.validation.BindingResult.class);

        org.springframework.validation.FieldError fieldError =
                new org.springframework.validation.FieldError("object", "field", "must not be null");

        org.mockito.Mockito.when(ex.getBindingResult()).thenReturn(bindingResult);
        org.mockito.Mockito.when(bindingResult.getFieldErrors())
                .thenReturn(java.util.List.of(fieldError));

        ResponseEntity<String> response = handler.handleValidationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("field"));
    }

    @Test
    void handleNotReadable() {
        HttpInputMessage inputMessage = org.mockito.Mockito.mock(HttpInputMessage.class);

        HttpMessageNotReadableException ex =
                new HttpMessageNotReadableException("Bad JSON", inputMessage);

        ResponseEntity<String> response = handler.handleNotReadable(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Некорректный JSON"));
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