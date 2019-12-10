package com.coreangel.managely.controller;

import com.coreangel.managely.dto.auth.RequestLoginToAccount;
import com.coreangel.managely.dto.auth.RequestRegisterAccount;
import com.coreangel.managely.dto.jwt.ResponseJwtToken;
import com.coreangel.managely.model.Account;
import com.coreangel.managely.model.AccountRole;
import com.coreangel.managely.security.validation.exceptions.AccountAlreadyExists;
import com.coreangel.managely.security.validation.exceptions.CustomException;
import com.coreangel.managely.security.validation.exceptions.WrongLoginData;
import com.coreangel.managely.service.AccountService;
import com.coreangel.managely.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AccountService accountService;
    private final JwtTokenUtil jwtUtil;

    @Autowired
    public AuthController(AccountService accountService, JwtTokenUtil jwtUtil) {
        this.accountService = accountService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public Account registerAccount(@Valid @RequestBody RequestRegisterAccount request) throws CustomException {

        Account account = Account.builder()
                .email(request.getEmail())
                .login(request.getLogin())
                .password(request.getPassword())
                .build();

        account.addRole(AccountRole.USER);

        Optional<Account> optAccount = this.accountService.createAccount(account);
        if (optAccount.isEmpty()) {
            throw new AccountAlreadyExists(account.getLogin());
        }

        return optAccount.get();
    }

    @PostMapping("/login")
    public ResponseJwtToken loginToAccount(@Valid @RequestBody RequestLoginToAccount request) throws CustomException {

        Optional<Account> optAccount = this.accountService.findAccountByLogin(request.getLogin());
        if (optAccount.isEmpty() || !optAccount.get().isPasswordMatch(request.getPassword())) {
            throw new WrongLoginData();
        }

        String token = this.jwtUtil.generateToken(optAccount.get());

        return new ResponseJwtToken(token);
    }

}
