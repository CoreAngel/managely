package com.coreangel.managely.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecuredPathController {

    @GetMapping("/xd")
    public String doSth() {
        return "gfdg";
    }

}
