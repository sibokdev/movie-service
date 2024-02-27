package com.code.challenge.controllers;

import com.code.challenge.config.CustomProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final CustomProperties customProperties;

    public HomeController(CustomProperties customProperties){
        this.customProperties = customProperties;
    }
    @GetMapping("/")
    public String getGreeting() {
        return customProperties.getGreeting();
    }
}
