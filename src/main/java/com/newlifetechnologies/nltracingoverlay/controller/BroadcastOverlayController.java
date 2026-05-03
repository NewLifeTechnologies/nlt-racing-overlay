package com.newlifetechnologies.nltracingoverlay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newlifetechnologies.nltracingoverlay.dto.BroadcastRelativeDTO;
import com.newlifetechnologies.nltracingoverlay.dto.OverlayDTO;
import com.newlifetechnologies.nltracingoverlay.service.LmuApiService;

@RestController
public class BroadcastOverlayController {

    private final LmuApiService lmuApiService;

    public BroadcastOverlayController(LmuApiService lmuApiService) {
        this.lmuApiService = lmuApiService;
    }

    @GetMapping(value = "/overlay/broadcast/selected-car")
    public OverlayDTO getSelectedCarOverlay() {
        return lmuApiService.getBroadcastSelectedCarOverlay();
    }
    
    @GetMapping("/overlay/broadcast/relative")
    public BroadcastRelativeDTO getBroadcastRelative() {
        return lmuApiService.getBroadcastRelative();
    }
}