package com.bestimol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.bestimol.SaveEntityResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<SaveEntityResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        SaveEntityResponse response = new SaveEntityResponse(false, "Operation failed" , null  , ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SaveEntityResponse> handleGenericException(Exception ex) {
        SaveEntityResponse response = new SaveEntityResponse(false, "Operation failed", null , "An unexpected error occurred");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}