package com.newlifetechnologies.nltracingoverlay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newlifetechnologies.nltracingoverlay.dto.ProfileDTO;
import com.newlifetechnologies.nltracingoverlay.service.LmuApiService;

@RestController
public class ProfileController {

    private final LmuApiService lmuApiService;

    public ProfileController(LmuApiService lmuApiService) {
        this.lmuApiService = lmuApiService;
    }

    @GetMapping("/profile")
    public ProfileDTO getProfile() {
        return lmuApiService.getProfile();
    }
}