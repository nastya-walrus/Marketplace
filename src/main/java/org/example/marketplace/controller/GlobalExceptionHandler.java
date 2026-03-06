package org.example.marketplace.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.example.marketplace.exception.EntityConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.swing.*;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<String> onRuntimeException(RuntimeException exception) {
        LOG.error(exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<String> onConstraintViolationException(ConstraintViolationException exception) {
        LOG.warn(exception.getMessage(), exception);
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        String message = "Неправильно заполнены поля: %n%s".formatted(
                constraintViolations.stream()
                        .map(it -> "Значение \"%s\" поля \"%s\" %s".formatted(
                                it.getInvalidValue(),
                                it.getPropertyPath(),
                                it.getMessage()
                                ))
                        .collect(Collectors.joining("\n"))
        );
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> onEntityNotFoundException(EntityNotFoundException exception) {
        LOG.warn(exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<String> onEmailConflictException(EntityConflictException exception) {
        LOG.warn(exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }
}
