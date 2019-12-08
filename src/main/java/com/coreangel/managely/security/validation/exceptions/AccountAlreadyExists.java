package com.coreangel.managely.security.validation.exceptions;

import org.springframework.http.HttpStatus;

public class AccountAlreadyExists extends CustomException {
    public AccountAlreadyExists(String login) {
        super(HttpStatus.CONFLICT, "User with login '" + login + "' already exists");
    }
}
