package com.newlifetechnologies.nltracingoverlay.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newlifetechnologies.nltracingoverlay.dto.OverlayDTO;
import com.newlifetechnologies.nltracingoverlay.dto.ProfileDTO;
import com.newlifetechnologies.nltracingoverlay.dto.StandingDTO;
import com.newlifetechnologies.nltracingoverlay.formatter.OverlayFormatter;

@Service
public class LmuApiService {

	@Value("${lmu.api.base-url}")
	private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final OverlayFormatter overlayFormatter;

    public LmuApiService(OverlayFormatter overlayFormatter) {
        this.overlayFormatter = overlayFormatter;
    }
    
    public ProfileDTO getProfile() {
        String endPoint = baseUrl.concat("/rest/profile/");

        try {
            String responseBody = restTemplate.getForObject(endPoint, String.class);
            return objectMapper.readValue(responseBody, ProfileDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar profile do LMU", e);
        }
    }
    
    public String getRawStandings() {
    	String endPoint = baseUrl.concat("/rest/watch/standings");
        return restTemplate.getForObject(endPoint, String.class);
    }
    
    public List<StandingDTO> getStandings() {
        String endPoint = baseUrl.concat("/rest/watch/standings");

        try {
            String responseBody = restTemplate.getForObject(endPoint, String.class);
            StandingDTO[] standingsArray = objectMapper.readValue(responseBody, StandingDTO[].class);
            return Arrays.asList(standingsArray);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar standings do LMU", e);
        }
    }
    
    public StandingDTO getStandingByPlayer() {
        List<StandingDTO> standings = getStandings();

        return standings.stream()
                .filter(StandingDTO::isPlayer)
                .findFirst()
                .orElse(null);
    }
    
    public StandingDTO getStandingByName() {
        ProfileDTO profile = getProfile();
        
        if (profile == null || profile.getName() == null || profile.getName().isBlank()) {
        	return null;
        }
        
        String playerName = profile.getName();

        return getStandings().stream()
                .filter(s -> s.getDriverName() != null &&
                             s.getDriverName().equalsIgnoreCase(playerName))
                .findFirst()
                .orElse(null);
    }
    
    public OverlayDTO buildOverlay(StandingDTO standing) {

        if (standing == null) {
            return null;
        }

        OverlayDTO overlay = new OverlayDTO();

        overlay.setDriverName(standing.getDriverName());
        overlay.setPosition(standing.getPosition());
        overlay.setVehicleName(standing.getVehicleName());

        overlay.setBestLapTime(overlayFormatter.formatTime(standing.getBestLapTime()));
        overlay.setLastLapTime(overlayFormatter.formatTime(standing.getLastLapTime()));

        overlay.setFuelPercent((int) (standing.getFuelFraction() * 100));

        return overlay;
    } 
}