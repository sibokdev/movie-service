package com.code.challenge.controllers;

import com.code.challenge.config.CustomProperties;
import com.code.challenge.config.Generated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final CustomProperties customProperties;

    public HomeController(CustomProperties customProperties){
        this.customProperties = customProperties;
    }
    @Generated
    @GetMapping("/")
    public String getGreeting() {
        return customProperties.getGreeting();
    }
}
