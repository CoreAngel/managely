package com.coreangel.managely.controller;

import com.coreangel.managely.account.Account;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @PostMapping("/register")
    public Account creteAccount(@RequestBody Account account) {
        System.out.println(account);
        return null;
    }

}
