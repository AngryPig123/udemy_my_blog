package org.example.rest_practice.exception.advice;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.example.rest_practice.exception.BlogApiException;
import org.example.rest_practice.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails<String>> resourceNotFoundExceptionHandler(
            ResourceNotFoundException resourceNotFoundException, WebRequest webRequest
    ) {

        ErrorDetails<String> errorDetails = new ErrorDetails<>(resourceNotFoundException.getMessage(), webRequest.getDescription(false));
        log.info("resourceNotFoundExceptionHandler controllerAdviceResponse = {}", errorDetails);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetails<String>> blogApiExceptionHandler(
            BlogApiException blogApiException, WebRequest webRequest
    ) {
        ErrorDetails<String> errorDetails = new ErrorDetails<>(blogApiException.getMessage(), webRequest.getDescription(false));
        log.info("blogApiExceptionHandler controllerAdviceResponse = {}", errorDetails);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

/*
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails<String>> accessDeniedExceptionHandler(
            AccessDeniedException exception, WebRequest webRequest
    ) {
        ErrorDetails<String> errorDetails = new ErrorDetails<>(exception.getMessage(), webRequest.getDescription(false));
        log.info("accessDeniedException controllerAdviceResponse = {}", errorDetails);
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }
*/

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails<String>> globalExceptionHandler(
            Exception exception, WebRequest webRequest
    ) {
        ErrorDetails<String> errorDetails = new ErrorDetails<>(exception.getMessage(), webRequest.getDescription(false));
        log.info("exception controllerAdviceResponse = {}", errorDetails);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest webRequest) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        ErrorDetails<Map<String, String>> errorDetails = new ErrorDetails<>(errors, webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorDetails<T> {

        private String localDateTime;

        private T message;
        private String details;

        public ErrorDetails(T message, String details) {
            this.localDateTime = LocalDateTime.now().toString();
            this.message = message;
            this.details = details;
        }

    }

}
