package com.newlifetechnologies.nltracingoverlay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newlifetechnologies.nltracingoverlay.dto.BroadcastClassStandingsDTO;
import com.newlifetechnologies.nltracingoverlay.dto.BroadcastRelativeDTO;
import com.newlifetechnologies.nltracingoverlay.dto.OverlayDTO;
import com.newlifetechnologies.nltracingoverlay.service.LmuApiService;
import com.newlifetechnologies.nltracingoverlay.service.StandingService;

@RestController
@RequestMapping("/widgets/broadcast/")
public class BroadcastWidgetsController {

    private final LmuApiService lmuApiService;
   private final StandingService standingService;

    public BroadcastWidgetsController(LmuApiService lmuApiService, StandingService standingService) {
        this.lmuApiService = lmuApiService;
        this.standingService = standingService;
    }
    
    @GetMapping("selected-car")
    public OverlayDTO getSelectedCarOverlay() {
        return lmuApiService.getBroadcastSelectedCarOverlay();
    }
    
    @GetMapping("relative")
    public BroadcastRelativeDTO getBroadcastRelative() {
        return lmuApiService.getBroadcastRelative();
    }

    @GetMapping("class-standings")
    public BroadcastClassStandingsDTO getBroadcastClassStandings(
            @RequestParam(required = false) String carClass) {

        return standingService.buildBroadcastClassStandings(carClass);
    }
}