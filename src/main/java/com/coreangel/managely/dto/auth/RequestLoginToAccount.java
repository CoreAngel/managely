package com.coreangel.managely.dto.auth;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RequestLoginToAccount {

    @NotNull(message = "Login cannot be empty")
    @Size(min = 6, max = 24, message = "Login length must be between 6 and 24 characters")
    private final String login;

    @NotNull(message = "Password cannot be empty")
    @Size(min = 6, max = 36, message = "Password length must be between 6 and 36 characters")
    private final String password;

    public RequestLoginToAccount(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
