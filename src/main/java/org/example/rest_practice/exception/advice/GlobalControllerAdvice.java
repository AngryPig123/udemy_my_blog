package org.example.rest_practice.exception.advice;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.example.rest_practice.exception.BlogApiException;
import org.example.rest_practice.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundExceptionHandler(
            ResourceNotFoundException resourceNotFoundException, WebRequest webRequest
    ) {

        ErrorDetails errorDetails = new ErrorDetails(resourceNotFoundException.getMessage(), webRequest.getDescription(false));
        log.info("resourceNotFoundExceptionHandler controllerAdviceResponse = {}", errorDetails);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetails> blogApiExceptionHandler(
            BlogApiException blogApiException, WebRequest webRequest
    ) {
        ErrorDetails errorDetails = new ErrorDetails(blogApiException.getMessage(), webRequest.getDescription(false));
        log.info("blogApiExceptionHandler controllerAdviceResponse = {}", errorDetails);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalExceptionHandler(
            Exception exception, WebRequest webRequest
    ) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), webRequest.getDescription(false));
        log.info("blogApiExceptionHandler controllerAdviceResponse = {}", errorDetails);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorDetails {

        private String localDateTime;

        private String message;
        private String details;

        public ErrorDetails(String message, String details) {
            this.localDateTime = LocalDateTime.now().toString();
            this.message = message;
            this.details = details;
        }

    }

}
