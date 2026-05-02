package com.newlifetechnologies.nltracingoverlay.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newlifetechnologies.nltracingoverlay.dto.BroadcastRelativeDTO;
import com.newlifetechnologies.nltracingoverlay.dto.CarDTO;
import com.newlifetechnologies.nltracingoverlay.dto.OverlayDTO;
import com.newlifetechnologies.nltracingoverlay.dto.ProfileDTO;
import com.newlifetechnologies.nltracingoverlay.dto.RelativeCarDTO;
import com.newlifetechnologies.nltracingoverlay.dto.StandingDTO;
import com.newlifetechnologies.nltracingoverlay.formatter.OverlayFormatter;

@Service
public class LmuApiService {

	@Value("${lmu.api.base-url}")
	private String baseUrl;
	private String lastCarId;
	private List<CarDTO> carsCache;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OverlayFormatter overlayFormatter;
    private JsonNode garageDataCache;
    private Map<String, Double> tankCapacityCache = new HashMap<>();

    public LmuApiService(OverlayFormatter overlayFormatter) {
        this.overlayFormatter = overlayFormatter;
    }
    
    public ProfileDTO getProfile() {
        String endPoint = baseUrl.concat("/rest/profile/");

        try {
        	String responseBody = getLmuResponseBody(endPoint);
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
        	String responseBody = getLmuResponseBody(endPoint);
            StandingDTO[] standingsArray = objectMapper.readValue(responseBody, StandingDTO[].class);
            return Arrays.asList(standingsArray);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar standings do LMU", e);
        }
    }
    
    private String getLmuResponseBody(String endPoint) {
        byte[] responseBytes = restTemplate.getForObject(endPoint, byte[].class);
        return new String(responseBytes, java.nio.charset.StandardCharsets.UTF_8);
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
        CarDTO car = getCarById(standing.getCarId());
        String seriesName = "-";
        String carClass = "-";
        String vehicleName = standing.getVehicleName();
        
        
        double fuelFraction = standing.getFuelFraction();
        double tankCapacity = getTankCapacity(standing.getCarId());
        double virtualEnergyPercent = Math.round(standing.getVeFraction() * 1000.0)/10.0;
        double fuelLiters = tankCapacity * fuelFraction;

        fuelLiters = Math.round(fuelLiters * 10.0) / 10.0;
        overlay.setFuelLiters(fuelLiters);
        overlay.setFuelPercent((int) (fuelFraction * 100));
        
        
        if (car != null && car.getDisplayProperties() != null) {
        	
        	if (car.getDisplayProperties().getSeriesName() != null) {
        		seriesName = car.getDisplayProperties().getSeriesName();
        	}
        	
        	if (car.getDisplayProperties().getCarClass() != null) {
        		carClass = car.getDisplayProperties().getCarClass();
        	}
        	
        	if (car.getDisplayProperties().getCarModel() != null) {
        		vehicleName = car.getDisplayProperties().getCarModel();	
        	}

        }

        overlay.setDriverName(standing.getDriverName());
        overlay.setPosition(standing.getPosition());
        overlay.setSeriesName(seriesName);
        overlay.setCarClass(carClass);
        overlay.setVehicleName(vehicleName);
        overlay.setBestLapTime(overlayFormatter.formatTime(standing.getBestLapTime()));
        overlay.setLastLapTime(overlayFormatter.formatTime(standing.getLastLapTime()));
        overlay.setFuelPercent((double) (standing.getFuelFraction() * 1000.0 / 10.0));
        overlay.setVirtualEnergyPercent(virtualEnergyPercent);
        overlay.setFuelLiters(fuelLiters);

        return overlay;
    }

    private RelativeCarDTO buildRelativeCar(StandingDTO standing, String interval) {

        if (standing == null) {
            return null;
        }

        RelativeCarDTO dto = new RelativeCarDTO();
        CarDTO car = getCarById(standing.getCarId());

        double virtualEnergyPercent = Math.round(standing.getVeFraction() * 1000.0) / 10.0;

        dto.setPosition(standing.getPosition());
        dto.setVirtualEnergyPercent(virtualEnergyPercent);
        dto.setDriverName(standing.getDriverName());
        dto.setInterval(interval);
        dto.setCarClass(car.getDisplayProperties().getCarClass());

        return dto;
    }
    
    public BroadcastRelativeDTO getBroadcastRelative() {

        StandingDTO focused = getStandingByWatchFocus();

        if (focused == null) {
            return null;
        }

        StandingDTO ahead = getStandingAhead(focused);
        StandingDTO behind = getStandingBehind(focused);
        BroadcastRelativeDTO dto = new BroadcastRelativeDTO();
        String intervalAhead = overlayFormatter.formatRelativeInterval(focused.getTimeBehindNext(), true);
        String intervalBehind = "-";

        if (behind != null) {
            intervalBehind = overlayFormatter.formatRelativeInterval(behind.getTimeBehindNext(), false);
        }

        dto.setAhead(buildRelativeCar(ahead, intervalAhead));
        dto.setFocused(buildRelativeCar(focused, ""));
        dto.setBehind(buildRelativeCar(behind, intervalBehind));

        return dto;
    }
    
    
    public List<CarDTO> getCars() {
        if (carsCache != null) {
            return carsCache;
        }

        String endPoint = baseUrl.concat("/rest/race/car");
        try {
        	String responseBody = getLmuResponseBody(endPoint);
            CarDTO[] carsArray = objectMapper.readValue(responseBody, CarDTO[].class);
            carsCache = Arrays.asList(carsArray);
            return carsCache;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar lista de carros do LMU", e);
        }
    }
    
    public void clearCarsCache() {
        carsCache = null;
    }
    
    public void clearGarageDataCache() {
        garageDataCache = null;
        lastCarId = null;
    }
    
    public CarDTO getCarById(String carId) {
        if (carId == null || carId.isBlank()) {
            return null;
        }

        CarDTO car = getCars().stream()
                .filter(c -> c.getId() != null && c.getId().equalsIgnoreCase(carId))
                .findFirst()
                .orElse(null);

        fillDisplayPropertiesDerivedFields(car);

        return car;
    }
    
    private void fillDisplayPropertiesDerivedFields(CarDTO car) {
        if (car == null || car.getDisplayProperties() == null) {
            return;
        }

        String fullTreePath = car.getDisplayProperties().getFullTreePath();

        if (fullTreePath == null || fullTreePath.isBlank()) {
            return;
        }

        String[] parts = fullTreePath.split(",");

        String seriesName = parts.length > 0 ? parts[0].trim() : "-";
        String carClass = parts.length > 1 ? parts[1].trim() : "-";
        String carModel = parts.length > 2 ? parts[2].trim() : "-";

        car.getDisplayProperties().setSeriesName(seriesName);
        car.getDisplayProperties().setCarClass(carClass);
        car.getDisplayProperties().setCarModel(carModel);
    }
    
    public OverlayDTO getPlayerOverlay() {
        StandingDTO standing = getStandingByName();
        return buildOverlay(standing);
    }
    
    public JsonNode getGarageData(String currentCarId) {

        if (lastCarId == null || !lastCarId.equals(currentCarId)) {
            garageDataCache = null;
            lastCarId = currentCarId;
        }

        if (garageDataCache != null) {
            return garageDataCache;
        }

        String endPoint = baseUrl.concat("/rest/garage/getPlayerGarageData");

        try {
        	String responseBody = getLmuResponseBody(endPoint);
            garageDataCache = objectMapper.readTree(responseBody);
            return garageDataCache;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar garage data do LMU", e);
        }
    }
    
    public double getTankCapacity(String currentCarId) {
    	
        if (tankCapacityCache.containsKey(currentCarId)) {
            return tankCapacityCache.get(currentCarId);
        }
        JsonNode garageData = getGarageData(currentCarId);
        JsonNode fuelLevelNode = garageData.path("VM_FUEL_LEVEL");
        JsonNode maxValueNode = fuelLevelNode.path("maxValue");
        JsonNode fuelCapacityNode = garageData.path("VM_FUEL_CAPACITY");
        JsonNode stringValueNode = fuelLevelNode.path("stringValue");
        String raw = maxValueNode.asText();
        String valueOnly = raw.split("L")[0];
        double fuelValue = Double.parseDouble(maxValueNode.asText()) > Double.parseDouble(valueOnly) ?
        		Double.parseDouble(maxValueNode.asText()) : Double.parseDouble(valueOnly);

        if (maxValueNode.isMissingNode() || !maxValueNode.isNumber()) {
            return 0;
        }

        tankCapacityCache.put(currentCarId, fuelValue);
        
        return fuelValue;
    }
    
    public StandingDTO getStandingByWatchFocus() {
        return getStandings().stream()
                .filter(s -> s.isHasFocus() || s.isFocus())
                .findFirst()
                .orElse(null);
    }
    
    public OverlayDTO getBroadcastSelectedCarOverlay() {
        StandingDTO standing = getStandingByWatchFocus();
        return buildOverlay(standing);
    }
    
    private StandingDTO getStandingBehind(StandingDTO focusedStanding) {

        if (focusedStanding == null) {
            return null;
        }

        int behindPosition = focusedStanding.getPosition() + 1;

        return getStandings().stream()
                .filter(s -> s.getPosition() == behindPosition)
                .findFirst()
                .orElse(null);
    }
    
    private StandingDTO getStandingAhead(StandingDTO focusedStanding) {

        if (focusedStanding == null) {
            return null;
        }
/*
 * Aqui posso verificar para não pegar a Posição - 1
 * pegar a linha do JSON acima da que estou usando 
 * para tentar montar um relative geral
 */
        int aheadPosition = focusedStanding.getPosition() - 1;

        if (aheadPosition < 1) {
            return null;
        }

        return getStandings().stream()
                .filter(s -> s.getPosition() == aheadPosition)
                .findFirst()
                .orElse(null);
    }
    
}