package com.newlifetechnologies.nltracingoverlay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newlifetechnologies.nltracingoverlay.dto.PilotRelativeDTO;
import com.newlifetechnologies.nltracingoverlay.service.StandingService;

@RestController
@RequestMapping("/widgets/pilot/")
public class PilotWidgetsController {

    private final StandingService standingService;

    public PilotWidgetsController(StandingService standingService) {
        this.standingService = standingService;
    }

    @GetMapping("relative")
    public PilotRelativeDTO getPilotRelative() {
        return standingService.buildPilotRelative();
    }
}
