package com.newlifetechnologies.nltracingoverlay.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newlifetechnologies.nltracingoverlay.dto.OverlayDTO;
import com.newlifetechnologies.nltracingoverlay.dto.StandingDTO;
import com.newlifetechnologies.nltracingoverlay.service.LmuApiService;

@RestController
public class StandingsController {

    private final LmuApiService lmuApiService;

    public StandingsController(LmuApiService lmuApiService) {
        this.lmuApiService = lmuApiService;
    }

    @GetMapping("/standings/raw")
    public String getRawStandings() {
        return lmuApiService.getRawStandings();
    }
    
    @GetMapping("/standings")
    public List<StandingDTO> getStandings() {
        return lmuApiService.getStandings();
    }
    
    @GetMapping("/standings/player")
    public StandingDTO getStandingByPlayer() {
        return lmuApiService.getStandingByPlayer();
    }
    
    @GetMapping("/standings/name")
    public StandingDTO getStandingByName() {
        return lmuApiService.getStandingByName();
    }
    
    @GetMapping("/overlay/player")
    public OverlayDTO getOverlay() {
        return lmuApiService.buildOverlay(
                lmuApiService.getStandingByName()
        );
    }
    
}