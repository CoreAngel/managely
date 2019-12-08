package com.coreangel.managely.security.validation;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
public class ApiError {

    private HttpStatus status;
    private int statusCode;
    private List<String> errors;

    public ApiError(HttpStatus status, List<String> errors) {
        super();
        this.status = status;
        this.statusCode = status.value();
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String error) {
        super();
        this.status = status;
        this.statusCode = status.value();
        errors = Collections.singletonList(error);
    }

}
