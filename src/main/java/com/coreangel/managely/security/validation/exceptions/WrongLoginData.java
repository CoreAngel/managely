package com.coreangel.managely.security.validation.exceptions;

import org.springframework.http.HttpStatus;

public class WrongLoginData extends CustomException {

    public WrongLoginData() {
        super(HttpStatus.UNAUTHORIZED, "Wrong login or password");
    }

}
