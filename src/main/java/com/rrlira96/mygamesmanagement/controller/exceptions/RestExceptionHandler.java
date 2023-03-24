package com.rrlira96.mygamesmanagement.controller.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.rrlira96.mygamesmanagement.service.exceptions.ResourceAlreadyExistsException;
import com.rrlira96.mygamesmanagement.service.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> ResourceNotFoundHandler(ResourceNotFoundException exception, HttpServletRequest request) {
        String title = "Not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError(Instant.now(), status.value(), title, exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<StandardError> ResourceAlreadyExistsHandler(ResourceAlreadyExistsException exception, HttpServletRequest request) {
        String title = "Resource already exists";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError(Instant.now(), status.value(), title, exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<StandardError> InvalidFormatExceptionHandler(InvalidFormatException exception, HttpServletRequest request) {
        String title = "Invalid number type";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError(Instant.now(), status.value(), title, exception.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }


}
