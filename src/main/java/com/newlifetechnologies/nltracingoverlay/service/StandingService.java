package com.newlifetechnologies.nltracingoverlay.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.newlifetechnologies.nltracingoverlay.dto.BroadcastClassStandingsDTO;
import com.newlifetechnologies.nltracingoverlay.dto.BroadcastRelativeDTO;
import com.newlifetechnologies.nltracingoverlay.dto.CarDTO;
import com.newlifetechnologies.nltracingoverlay.dto.ClassStandingDTO;
import com.newlifetechnologies.nltracingoverlay.dto.RelativeCarDTO;
import com.newlifetechnologies.nltracingoverlay.dto.StandingDTO;
import com.newlifetechnologies.nltracingoverlay.formatter.OverlayFormatter;

@Service
public class StandingService {
	
	private final LmuApiService lmuApiService;
	private final OverlayFormatter overlayFormatter;
    
    public StandingService(LmuApiService lmuApiService, OverlayFormatter overlayFormatter) {
        this.lmuApiService = lmuApiService;
        this.overlayFormatter = overlayFormatter;
    }

    public Map<String, List<ClassStandingDTO>> buildClassStandings(List<StandingDTO> standings) {

        // Agrupa por classe
        Map<String, List<StandingDTO>> groupedByClass =
                standings.stream()
                        .collect(Collectors.groupingBy(StandingDTO::getCarClass));

        Map<String, List<ClassStandingDTO>> result = new HashMap<>();

        for (Map.Entry<String, List<StandingDTO>> entry : groupedByClass.entrySet()) {

            String carClass = entry.getKey();

            List<StandingDTO> classCars = entry.getValue();

            // Ordena pela posição geral
            classCars.sort(Comparator.comparingInt(StandingDTO::getPosition));

            List<ClassStandingDTO> classStandingList = new ArrayList<>();

            int classPosition = 1;

            for (StandingDTO car : classCars) {

                ClassStandingDTO dto = new ClassStandingDTO();

                dto.setCarClass(carClass);
                dto.setClassPosition(classPosition);
                dto.setOverallPosition(car.getPosition());

                dto.setCarId(car.getCarId());
                dto.setDriverName(car.getDriverName());
                dto.setVehicleName(car.getVehicleName());

                dto.setTimeBehindNext(car.getTimeBehindNext());
                dto.setTimeBehindClassLeader(car.getTimeBehindClassLeader());

                classStandingList.add(dto);

                classPosition++;
            }

            result.put(carClass, classStandingList);
        }

        return result;
    }
    
    private List<ClassStandingDTO> buildClassStandingRows(String targetClass, List<StandingDTO> classCars) {

        List<ClassStandingDTO> result = new ArrayList<>();
        int classPosition = 1;
        String bestLapCarId = findBestLapCarId(classCars);

        for (StandingDTO car : classCars) {

            ClassStandingDTO dto = new ClassStandingDTO();

            dto.setCarClass(targetClass);
            dto.setClassPosition(classPosition);
            dto.setOverallPosition(car.getPosition());

            dto.setCarId(car.getCarId());
            dto.setCarNumber(car.getCarNumber());
            dto.setDriverName(car.getDriverName());
            dto.setVehicleName(car.getVehicleName());

            dto.setPlayer(car.isPlayer());
            dto.setFocus(car.isFocus());

            dto.setTimeBehindNext(car.getTimeBehindNext());
            dto.setTimeBehindClassLeader(car.getTimeBehindClassLeader());

            dto.setLastLapTime(overlayFormatter.formatTime(car.getLastLapTime()));
            dto.setBestLapTime(overlayFormatter.formatTime(car.getBestLapTime()));
            dto.setBestLapInClass(car.getCarId() != null && car.getCarId().equals(bestLapCarId));

            result.add(dto);
            classPosition++;
        }

        return result;
    }
    
    public BroadcastClassStandingsDTO buildBroadcastClassStandings(String requestedClass) {

        // 🔹 1. Buscar dados do LMU
        List<StandingDTO> standings = lmuApiService.getStandings();

        if (standings == null || standings.isEmpty()) {
            return new BroadcastClassStandingsDTO();
        }

        // 🔹 2. Descobrir classe alvo
        /*
        String resolvedClass = requestedClass;

        if (resolvedClass == null || resolvedClass.isEmpty()) {
            resolvedClass = standings.stream()
                    .filter(StandingDTO::isFocus)
                    .findFirst()
                    .map(StandingDTO::getCarClass)
                    .orElse(null);
        }
        */
        
        String resolvedClass = resolveTargetClass(standings, requestedClass);

        if (resolvedClass == null || resolvedClass.isEmpty()) {
            return new BroadcastClassStandingsDTO();
        }

        final String targetClass = resolvedClass;

        // 🔹 3. Filtrar por classe
        /*
        List<StandingDTO> classCars = standings.stream()
                .filter(s -> targetClass.equals(s.getCarClass()))
                .sorted(Comparator.comparingInt(StandingDTO::getPosition))
                .toList();
                */
        List<StandingDTO> classCars = filterAndSortByClass(standings, targetClass);

        // 🔹 4. Montar lista com posição recalculada
        /*
        List<ClassStandingDTO> result = new ArrayList<>();
        int classPosition = 1;
        String bestLapCarId = findBestLapCarId(classCars);
        
        for (StandingDTO car : classCars) {

            ClassStandingDTO dto = new ClassStandingDTO();

            dto.setCarClass(targetClass);
            dto.setClassPosition(classPosition);
            dto.setOverallPosition(car.getPosition());

            dto.setCarId(car.getCarId());
            dto.setCarNumber(car.getCarNumber());
            dto.setDriverName(car.getDriverName());
            dto.setVehicleName(car.getVehicleName());

            dto.setPlayer(car.isPlayer());
            dto.setFocus(car.isFocus());

            dto.setTimeBehindNext(car.getTimeBehindNext());
            dto.setTimeBehindClassLeader(car.getTimeBehindClassLeader());
            
            dto.setLastLapTime(overlayFormatter.formatTime(car.getLastLapTime()));
            dto.setBestLapTime(overlayFormatter.formatTime(car.getBestLapTime()));
            dto.setBestLapInClass(car.getCarId() != null && car.getCarId().equals(bestLapCarId));

            result.add(dto);
            classPosition++;
        }
        */
        List<ClassStandingDTO> result = buildClassStandingRows(targetClass, classCars);

        return new BroadcastClassStandingsDTO(targetClass, result);
    }
    
    public BroadcastRelativeDTO buildBroadcastIntervalNeighbors(String requestedClass) {

        List<StandingDTO> standings = lmuApiService.getStandings();

        if (standings == null || standings.isEmpty()) {
            return new BroadcastRelativeDTO();
        }

        String targetClass = resolveTargetClass(standings, requestedClass);

        if (targetClass == null || targetClass.isEmpty()) {
            return new BroadcastRelativeDTO();
        }

        List<StandingDTO> classCars = filterAndSortByClass(standings, targetClass);

        if (classCars.isEmpty()) {
            return new BroadcastRelativeDTO();
        }

        List<ClassStandingDTO> classRows = buildClassStandingRows(targetClass, classCars);

        Map<String, Integer> classPositionByCarId = classRows.stream()
                .collect(Collectors.toMap(
                        ClassStandingDTO::getCarId,
                        ClassStandingDTO::getClassPosition
                ));
        
        int focusedIndex = -1;

        for (int i = 0; i < classRows.size(); i++) {
            if (classRows.get(i).isFocus()) {
                focusedIndex = i;
                break;
            }
        }

        if (focusedIndex < 0) {
            return new BroadcastRelativeDTO();
        }

        StandingDTO ahead = focusedIndex > 0 ? classCars.get(focusedIndex - 1) : null;
        StandingDTO focused = classCars.get(focusedIndex);
        StandingDTO behind = focusedIndex < classCars.size() - 1 ? classCars.get(focusedIndex + 1) : null;

        BroadcastRelativeDTO dto = new BroadcastRelativeDTO();

        dto.setAhead(toRelativeCarDTO(ahead,true
        		,getClassPosition(classPositionByCarId, ahead),
                focused != null ? focused.getTimeBehindNext() : 0));
        dto.setFocused(toRelativeCarDTO(focused,null,
                getClassPosition(classPositionByCarId, focused),0));
        dto.setBehind(toRelativeCarDTO(behind,false,
        		getClassPosition(classPositionByCarId, behind),
        		behind != null ? behind.getTimeBehindNext() : 0));

        return dto;
    }
    
    private RelativeCarDTO toRelativeCarDTO(StandingDTO standing, Boolean ahead, int classPosition, double intervalSeconds) {

        if (standing == null) {
            return null;
        }
        
        CarDTO carInfo = lmuApiService.getCarById(standing.getCarId());
        RelativeCarDTO dto = new RelativeCarDTO();

        dto.setPosition(classPosition);
        dto.setDriverName(standing.getDriverName());
        if (carInfo != null && carInfo.getDisplayProperties() != null) {
            dto.setCarClass(carInfo.getDisplayProperties().getCarClass());
        } else {
            dto.setCarClass(standing.getCarClass());
        }        
        dto.setVirtualEnergyPercent(Math.round(standing.getVeFraction() * 1000.0)/10.0);

        if (ahead == null) {
            dto.setInterval("-");
        } else {
            dto.setInterval(overlayFormatter.formatRelativeInterval(intervalSeconds, ahead));
        }

        return dto;
    }
    
    private String resolveTargetClass(List<StandingDTO> standings, String requestedClass) {
        if (requestedClass != null && !requestedClass.isEmpty()) {
            return requestedClass;
        }

        return standings.stream()
                .filter(StandingDTO::isFocus)
                .findFirst()
                .map(StandingDTO::getCarClass)
                .orElse(null);
    }

    private List<StandingDTO> filterAndSortByClass(List<StandingDTO> standings, String targetClass) {
        return standings.stream()
                .filter(s -> targetClass.equals(s.getCarClass()))
                .sorted(Comparator.comparingInt(StandingDTO::getPosition))
                .toList();
    }
    
    private String findBestLapCarId(List<StandingDTO> classCars) {
        return classCars.stream()
                .filter(car -> car.getBestLapTime() > 0)
                .min(Comparator.comparingDouble(StandingDTO::getBestLapTime))
                .map(StandingDTO::getCarId)
                .orElse(null);
    }
    
    private int getClassPosition(Map<String, Integer> classPositionByCarId, StandingDTO car) {

        if (car == null || car.getCarId() == null) {
            return 0;
        }

        return classPositionByCarId.getOrDefault(car.getCarId(), 0);
    }
}