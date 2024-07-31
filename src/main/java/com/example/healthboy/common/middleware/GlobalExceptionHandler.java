package com.example.healthboy.common.middleware;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.healthboy.common.ApplicationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> globalExceptionHandler(Exception ex, WebRequest request) {
        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = ex.getMessage();
        logger.severe("Exception: [" + status + "] " + message);
        return new ResponseEntity<String>(ex.getMessage(), status);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<String> handleCustomException(ApplicationException ex) {
        HttpStatus status = ex.getStatus();
        String message = ex.getMessage();
        logger.warning("ApplicationException: [" + status + "] " + message);
        return new ResponseEntity<String>(message, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        logger.warning("MethodArgumentNotValidException: " + errors);

        return new ResponseEntity<>(errors, headers, status);
    }
}