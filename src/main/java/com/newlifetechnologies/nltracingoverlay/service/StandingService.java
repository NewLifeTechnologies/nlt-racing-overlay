package com.newlifetechnologies.nltracingoverlay.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.newlifetechnologies.nltracingoverlay.dto.BroadcastClassStandingsDTO;
import com.newlifetechnologies.nltracingoverlay.dto.ClassStandingDTO;
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
    
    public BroadcastClassStandingsDTO buildBroadcastClassStandings(String requestedClass) {

        // 🔹 1. Buscar dados do LMU
        List<StandingDTO> standings = lmuApiService.getStandings();

        if (standings == null || standings.isEmpty()) {
            return new BroadcastClassStandingsDTO();
        }

        // 🔹 2. Descobrir classe alvo
        String resolvedClass = requestedClass;

        if (resolvedClass == null || resolvedClass.isEmpty()) {
            resolvedClass = standings.stream()
                    .filter(StandingDTO::isFocus)
                    .findFirst()
                    .map(StandingDTO::getCarClass)
                    .orElse(null);
        }

        if (resolvedClass == null || resolvedClass.isEmpty()) {
            return new BroadcastClassStandingsDTO();
        }

        final String targetClass = resolvedClass;

        // 🔹 3. Filtrar por classe
        List<StandingDTO> classCars = standings.stream()
                .filter(s -> targetClass.equals(s.getCarClass()))
                .sorted(Comparator.comparingInt(StandingDTO::getPosition))
                .toList();

        // 🔹 4. Montar lista com posição recalculada
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

        return new BroadcastClassStandingsDTO(targetClass, result);
    }    
    
    private String findBestLapCarId(List<StandingDTO> classCars) {
        return classCars.stream()
                .filter(car -> car.getBestLapTime() > 0)
                .min(Comparator.comparingDouble(StandingDTO::getBestLapTime))
                .map(StandingDTO::getCarId)
                .orElse(null);
    }
}