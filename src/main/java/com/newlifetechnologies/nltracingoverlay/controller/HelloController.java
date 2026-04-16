package com.newlifetechnologies.nltracingoverlay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newlifetechnologies.nltracingoverlay.dto.DriverDTO;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "NLT Race Overlay API is running";
    }

    @GetMapping("/driver")
    public DriverDTO getDriver() {
        return new DriverDTO("Patatas", 1);
    }
}