package com.newlifetechnologies.nltracingoverlay.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.newlifetechnologies.nltracingoverlay.dto.BroadcastClassStandingsDTO;
import com.newlifetechnologies.nltracingoverlay.dto.ConsistencyDTO;
import com.newlifetechnologies.nltracingoverlay.dto.BroadcastRelativeDTO;
import com.newlifetechnologies.nltracingoverlay.dto.CarDTO;
import com.newlifetechnologies.nltracingoverlay.dto.ClassStandingDTO;
import com.newlifetechnologies.nltracingoverlay.dto.PilotRelativeCarDTO;
import com.newlifetechnologies.nltracingoverlay.dto.PilotRelativeDTO;
import com.newlifetechnologies.nltracingoverlay.dto.PressureBehindDTO;
import com.newlifetechnologies.nltracingoverlay.dto.RelativeCarDTO;
import com.newlifetechnologies.nltracingoverlay.dto.PressureAheadDTO;
import com.newlifetechnologies.nltracingoverlay.dto.StandingDTO;
import com.newlifetechnologies.nltracingoverlay.formatter.OverlayFormatter;

@Service
public class StandingService {

	@Value("${nlt.overlay.gap-threshold:1.5}")
	private double gapThreshold;

	private final LmuApiService lmuApiService;
	private final OverlayFormatter overlayFormatter;
	private final GapHistoryService gapHistoryService;
	private final PressureAheadHistoryService pressureAheadHistoryService;
	private final LapHistoryService lapHistoryService;

    public StandingService(LmuApiService lmuApiService, OverlayFormatter overlayFormatter,
            GapHistoryService gapHistoryService, PressureAheadHistoryService pressureAheadHistoryService,
            LapHistoryService lapHistoryService) {
        this.lmuApiService = lmuApiService;
        this.overlayFormatter = overlayFormatter;
        this.gapHistoryService = gapHistoryService;
        this.pressureAheadHistoryService = pressureAheadHistoryService;
        this.lapHistoryService = lapHistoryService;
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

    public PilotRelativeDTO buildPilotRelative() {

        List<StandingDTO> standings = lmuApiService.getStandings();

        if (standings == null || standings.isEmpty()) {
            return new PilotRelativeDTO();
        }

        StandingDTO playerCar = standings.stream()
                .filter(StandingDTO::isPlayer)
                .findFirst()
                .orElse(null);

        if (playerCar == null) {
            return new PilotRelativeDTO();
        }

        String playerClass = playerCar.getCarClass();
        List<StandingDTO> classCars = filterAndSortByClass(standings, playerClass);

        int playerIndex = -1;
        for (int i = 0; i < classCars.size(); i++) {
            if (classCars.get(i).isPlayer()) {
                playerIndex = i;
                break;
            }
        }

        if (playerIndex < 0) {
            return new PilotRelativeDTO();
        }

        List<ClassStandingDTO> classRows = buildClassStandingRows(playerClass, classCars);

        Map<String, Integer> classPositionByCarId = classRows.stream()
                .collect(Collectors.toMap(ClassStandingDTO::getCarId, ClassStandingDTO::getClassPosition));

        StandingDTO aheadStanding = playerIndex > 0 ? classCars.get(playerIndex - 1) : null;
        StandingDTO behindStanding = playerIndex < classCars.size() - 1 ? classCars.get(playerIndex + 1) : null;

        PilotRelativeDTO dto = new PilotRelativeDTO();

        dto.setAhead(toPilotRelativeCarDTO(aheadStanding, playerCar, true,
                getClassPosition(classPositionByCarId, aheadStanding),
                playerCar.getTimeBehindNext()));

        dto.setBehind(toPilotRelativeCarDTO(behindStanding, playerCar, false,
                getClassPosition(classPositionByCarId, behindStanding),
                behindStanding != null ? behindStanding.getTimeBehindNext() : 0));

        return dto;
    }

    private PilotRelativeCarDTO toPilotRelativeCarDTO(StandingDTO standing, StandingDTO player,
            boolean isAhead, int classPosition, double intervalSeconds) {

        if (standing == null) {
            return null;
        }

        PilotRelativeCarDTO dto = new PilotRelativeCarDTO();

        dto.setPosition(classPosition);
        dto.setCarNumber(standing.getCarNumber());
        dto.setDriverName(standing.getDriverName());
        dto.setCarClass(standing.getCarClass());
        dto.setSameClass(standing.getCarClass() != null && standing.getCarClass().equals(player.getCarClass()));
        dto.setLapContext(buildLapContext(standing, player));
        dto.setLastLapTime(overlayFormatter.formatTime(standing.getLastLapTime()));
        dto.setPaceGap(overlayFormatter.formatPaceGap(player.getLastLapTime(), standing.getLastLapTime()));
        dto.setInterval(overlayFormatter.formatPilotInterval(intervalSeconds, isAhead));
        dto.setThreatStatus(computeThreatStatus(standing, player, isAhead));

        return dto;
    }

    private String buildLapContext(StandingDTO neighbor, StandingDTO player) {
        int diff = neighbor.getLapsCompleted() - player.getLapsCompleted();
        if (diff == 0) return "mesma volta";
        // neighbor com mais voltas está à frente → sinal negativo (como no interval)
        if (diff > 0) return "-" + diff + (diff > 1 ? " voltas" : " volta");
        int absDiff = Math.abs(diff);
        return "+" + absDiff + (absDiff > 1 ? " voltas" : " volta");
    }

    private String computeThreatStatus(StandingDTO neighbor, StandingDTO player, boolean isAhead) {
        boolean sameClass = neighbor.getCarClass() != null
                && neighbor.getCarClass().equals(player.getCarClass());
        boolean sameLap = neighbor.getLapsCompleted() == player.getLapsCompleted();

        if (!sameClass || !sameLap) {
            return "IGNORAR";
        }

        if (player.getLastLapTime() <= 0 || neighbor.getLastLapTime() <= 0) {
            return null;
        }

        double paceGap = neighbor.getLastLapTime() - player.getLastLapTime();

        if (isAhead && paceGap > 0) return "ALVO";
        if (!isAhead && paceGap < 0) return "AMEACA";

        return null;
    }

    public PressureBehindDTO buildPressureBehind() {
        List<StandingDTO> standings = lmuApiService.getStandings();

        if (standings == null || standings.isEmpty()) {
            gapHistoryService.reset();
            return new PressureBehindDTO();
        }

        StandingDTO player = standings.stream()
                .filter(StandingDTO::isPlayer)
                .findFirst()
                .orElse(null);

        if (player == null) {
            gapHistoryService.reset();
            return new PressureBehindDTO();
        }

        String playerClass = player.getCarClass();

        List<StandingDTO> eligibleCars = standings.stream()
                .filter(s -> playerClass.equals(s.getCarClass()))
                .filter(s -> !s.isPitting())
                .sorted(Comparator.comparingInt(StandingDTO::getPosition))
                .toList();

        int playerIndex = -1;
        for (int i = 0; i < eligibleCars.size(); i++) {
            if (eligibleCars.get(i).isPlayer()) {
                playerIndex = i;
                break;
            }
        }

        if (playerIndex < 0 || playerIndex >= eligibleCars.size() - 1) {
            gapHistoryService.reset();
            return new PressureBehindDTO();
        }

        StandingDTO behind = eligibleCars.get(playerIndex + 1);
        
        /*
        if (behind.getLapsCompleted() != player.getLapsCompleted()) {
            gapHistoryService.reset();
            return new PressureBehindDTO();
        }
        */

        double currentGap = behind.getTimeBehindNext();
        gapHistoryService.record(behind.getCarId(), behind.getLapsCompleted(), currentGap);

        List<Double> history = gapHistoryService.getHistory();

        if (history.size() < 2) {
            return new PressureBehindDTO();
        }

        double totalGain = 0;
        for (int i = 1; i < history.size(); i++) {
            totalGain += history.get(i - 1) - history.get(i);
        }
        double gainPerLap = totalGain / (history.size() - 1);

        boolean gapOk = gapThreshold <= 0 || currentGap < gapThreshold;
        if (gainPerLap <= 0 || !gapOk) {
            return new PressureBehindDTO();
        }

        int lapsUntilReach = (int) Math.ceil(currentGap / gainPerLap);

        PressureBehindDTO dto = new PressureBehindDTO();
        dto.setActive(true);
        dto.setCarNumber(behind.getCarNumber());
        dto.setDriverName(behind.getDriverName());
        dto.setGapSeconds(String.format(java.util.Locale.US, "%.3f", currentGap));
        dto.setGainPerLap(String.format(java.util.Locale.US, "%.1fs/volta", gainPerLap));
        dto.setLapsUntilReach(lapsUntilReach + (lapsUntilReach == 1 ? " volta" : " voltas"));

        return dto;
    }

    public PressureAheadDTO buildPressureAhead() {

        List<StandingDTO> standings = lmuApiService.getStandings();

        if (standings == null || standings.isEmpty()) {
            pressureAheadHistoryService.reset();
            return new PressureAheadDTO();
        }

        StandingDTO player = standings.stream()
                .filter(StandingDTO::isPlayer)
                .findFirst()
                .orElse(null);

        if (player == null) {
            pressureAheadHistoryService.reset();
            return new PressureAheadDTO();
        }

        String playerClass = player.getCarClass();

        List<StandingDTO> eligibleCars = standings.stream()
                .filter(s -> playerClass.equals(s.getCarClass()))
                .filter(s -> !s.isPitting())
                .sorted(Comparator.comparingInt(StandingDTO::getPosition))
                .toList();

        int playerIndex = -1;
        for (int i = 0; i < eligibleCars.size(); i++) {
            if (eligibleCars.get(i).isPlayer()) {
                playerIndex = i;
                break;
            }
        }

        if (playerIndex <= 0) {
            pressureAheadHistoryService.reset();
            return new PressureAheadDTO();
        }

        StandingDTO ahead = eligibleCars.get(playerIndex - 1);

        /*
        if (ahead.getLapsCompleted() != player.getLapsCompleted()) {
            pressureAheadHistoryService.reset();
            return new PressureAheadDTO();
        }
        */

        double currentGap = player.getTimeBehindNext();
        pressureAheadHistoryService.record(ahead.getCarId(), player.getLapsCompleted(), currentGap);

        List<Double> history = pressureAheadHistoryService.getHistory();

        if (history.size() < 2) {
            return new PressureAheadDTO();
        }

        double totalGain = 0;
        for (int i = 1; i < history.size(); i++) {
            totalGain += history.get(i - 1) - history.get(i);
        }
        double gainPerLap = totalGain / (history.size() - 1);

        boolean gapOk = gapThreshold <= 0 || currentGap < gapThreshold;
        if (gainPerLap <= 0 || !gapOk) {
            return new PressureAheadDTO();
        }

        int lapsUntilAttack = (int) Math.ceil(currentGap / gainPerLap);

        List<ClassStandingDTO> classRows = buildClassStandingRows(playerClass, eligibleCars);
        Map<String, Integer> classPositionByCarId = classRows.stream()
                .collect(Collectors.toMap(ClassStandingDTO::getCarId, ClassStandingDTO::getClassPosition));

        PressureAheadDTO dto = new PressureAheadDTO();
        dto.setActive(true);
        dto.setPosition(getClassPosition(classPositionByCarId, ahead));
        dto.setCarNumber(ahead.getCarNumber());
        dto.setDriverName(ahead.getDriverName());
        dto.setGapSeconds(String.format(java.util.Locale.US, "%.3f", currentGap));
        dto.setGainPerLap(String.format(java.util.Locale.US, "%.1fs/volta", gainPerLap));
        dto.setLapsUntilAttack(lapsUntilAttack + (lapsUntilAttack == 1 ? " volta" : " voltas"));

        return dto;
    }

    public ConsistencyDTO buildConsistency() {

        List<StandingDTO> standings = lmuApiService.getStandings();

        if (standings == null || standings.isEmpty()) {
            lapHistoryService.reset();
            return new ConsistencyDTO();
        }

        StandingDTO player = standings.stream()
                .filter(StandingDTO::isPlayer)
                .findFirst()
                .orElse(null);

        if (player == null) {
            lapHistoryService.reset();
            return new ConsistencyDTO();
        }

        lapHistoryService.record(player.getLapsCompleted(), player.getLastLapTime());

        List<Double> history = lapHistoryService.getHistory();

        if (history.size() < 2) {
            return new ConsistencyDTO();
        }

        double mean = history.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double variance = history.stream().mapToDouble(t -> Math.pow(t - mean, 2)).average().orElse(0);
        double stdDev = Math.sqrt(variance);
        double relativeVariation = (stdDev / mean) * 100;

        double bestRecent = history.stream().mapToDouble(Double::doubleValue).min().orElse(0);

        String status;
        if (relativeVariation <= 0.25) {
            status = "ESTAVEL";
        } else if (relativeVariation <= 0.60) {
            status = "ATENCAO";
        } else {
            status = "INSTAVEL";
        }

        ConsistencyDTO dto = new ConsistencyDTO();
        dto.setActive(true);
        dto.setLapCount(history.size());
        dto.setAverageLap(overlayFormatter.formatTime(mean));
        dto.setBestRecentLap(overlayFormatter.formatTime(bestRecent));
        dto.setVariation(String.format(java.util.Locale.US, "+/-%.3fs", stdDev));
        dto.setStatus(status);

        return dto;
    }
}