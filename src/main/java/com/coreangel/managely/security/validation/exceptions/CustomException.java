package com.coreangel.managely.security.validation.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
public class CustomException extends Exception {

    private HttpStatus status;
    private List<String> errors;

    public CustomException(HttpStatus status, List<String> errors) {
        this.status = status;
        this.errors = errors;
    }

    public CustomException(HttpStatus status, String error) {
        this.status = status;
        this.errors = Collections.singletonList(error);
    }

}
