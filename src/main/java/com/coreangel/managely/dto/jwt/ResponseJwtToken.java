package com.coreangel.managely.dto.jwt;

public class ResponseJwtToken {

    private final String token;

    public ResponseJwtToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
