package com.coreangel.managely.dto.auth;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RequestRegisterAccount {

    @NotNull(message = "Login cannot be empty")
    @Size(min = 6, max = 24, message = "Login length must be between 6 and 24 characters")
    private final String login;

    @NotNull(message = "Email cannot be empty")
    @Email(message = "Email field must be valid email format")
    private final String email;

    @NotNull(message = "Password cannot be empty")
    private final String password;

    @NotNull(message = "Repeat password cannot be empty")
    private final String repeatPassword;

    @AssertTrue(message = "Passwords must be equals")
    private boolean isPasswordsEquals() {
        return this.password.equals(this.repeatPassword);
    }

    public RequestRegisterAccount(String login, String email, String password, String repeatPassword) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }
}
