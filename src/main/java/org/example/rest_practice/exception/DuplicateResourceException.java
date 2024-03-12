package org.example.rest_practice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateResourceException extends RuntimeException {

    private final String resourceName;
    private final String fieldName;
    private final String fieldValue;

    public DuplicateResourceException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("This %s is already in use by %s at %s.", fieldName, resourceName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
