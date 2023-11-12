package com.sergeb.luxgatewayjava.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class IndexController {
    @GetMapping

    public String index(Principal principal) {
        return "How are we doing, " + principal.getName() + "?!";
    }

}