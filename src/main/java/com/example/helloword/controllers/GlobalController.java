package com.example.helloword.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalController {
    @GetMapping("/")
    public String healthCheck() {
        return "Hello world!";
    }
}
