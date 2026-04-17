package com.newlifetechnologies.nltracingoverlay.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newlifetechnologies.nltracingoverlay.dto.CarDTO;
import com.newlifetechnologies.nltracingoverlay.dto.OverlayDTO;
import com.newlifetechnologies.nltracingoverlay.dto.ProfileDTO;
import com.newlifetechnologies.nltracingoverlay.dto.StandingDTO;
import com.newlifetechnologies.nltracingoverlay.formatter.OverlayFormatter;

@Service
public class LmuApiService {

	@Value("${lmu.api.base-url}")
	private String baseUrl;
	private List<CarDTO> carsCache;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OverlayFormatter overlayFormatter;
    private JsonNode garageDataCache;
    private String lastCarId;

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
        CarDTO car = getCarById(standing.getCarId());
        String seriesName = "-";
        String carClass = "-";
        String vehicleName = standing.getVehicleName();
        
        
        double fuelFraction = standing.getFuelFraction();
        int tankCapacity = getTankCapacity(standing.getCarId());
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
    
    public List<CarDTO> getCars() {
        if (carsCache != null) {
            return carsCache;
        }

        String endPoint = baseUrl.concat("/rest/race/car");
        try {
            String responseBody = restTemplate.getForObject(endPoint, String.class);
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
    
    /*
    public JsonNode getGarageData() {
        if (garageDataCache != null) {
            return garageDataCache;
        }

        String endPoint = baseUrl.concat("/rest/garage/getPlayerGarageData");

        try {
            String responseBody = restTemplate.getForObject(endPoint, String.class);
            garageDataCache = objectMapper.readTree(responseBody);
            return garageDataCache;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar garage data do LMU", e);
        }
    }
    */
    
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
            String responseBody = restTemplate.getForObject(endPoint, String.class);
            garageDataCache = objectMapper.readTree(responseBody);
            return garageDataCache;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar garage data do LMU", e);
        }
    }
    
    /*
    public int getTankCapacity() {
        JsonNode garageData = getGarageData();

        JsonNode fuelLevelNode = garageData.path("VM_FUEL_LEVEL");
        JsonNode maxValueNode = fuelLevelNode.path("maxValue");

        if (maxValueNode.isMissingNode() || !maxValueNode.isNumber()) {
            return 0;
        }

        return maxValueNode.asInt();
    }
    */
    public int getTankCapacity(String currentCarId) {
        JsonNode garageData = getGarageData(currentCarId);

        JsonNode fuelLevelNode = garageData.path("VM_FUEL_LEVEL");
        JsonNode maxValueNode = fuelLevelNode.path("maxValue");

        if (maxValueNode.isMissingNode() || !maxValueNode.isNumber()) {
            return 0;
        }

        return maxValueNode.asInt();
    }
    
}