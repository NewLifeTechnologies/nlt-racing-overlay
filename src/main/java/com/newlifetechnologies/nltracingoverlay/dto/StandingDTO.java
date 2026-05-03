package com.newlifetechnologies.nltracingoverlay.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StandingDTO {

    private AttackModeDTO attackMode;

    private double bestLapSectorTime1;
    private double bestLapSectorTime2;
    private double bestLapTime;
    private double bestSectorTime1;
    private double bestSectorTime2;

    private VectorDTO carAcceleration;

    private String carClass;
    private String carId;
    private String carNumber;

    private CarPositionDTO carPosition;
    private VectorDTO carVelocity;

    private String countLapFlag;
    private double currentSectorTime1;
    private double currentSectorTime2;
    private String driverName;
    private boolean drsActive;
    private double estimatedLapTime;
    private String finishStatus;
    private String flag;
    private boolean focus;
    private double fuelFraction;
    private String fullTeamName;
    private String gamePhase;
    private boolean hasFocus;
    private boolean headlights;
    private int inControl;
    private boolean inGarageStall;
    private double lapDistance;
    private double lapStartET;
    private int lapsBehindClassLeader;
    private int lapsBehindLeader;
    private int lapsBehindNext;
    private int lapsCompleted;
    private double lastLapTime;
    private double lastSectorTime1;
    private double lastSectorTime2;
    private double pathLateral;
    private int penalties;
    private String pitGroup;
    private double pitLapDistance;
    private String pitState;
    private int pitstops;
    private boolean pitting;
    private boolean player;
    private int position;
    private int qualification;
    private String sector;
    private boolean serverScored;
    private int slotID;
    private long steamID;
    private double timeBehindClassLeader;
    private double timeBehindLeader;
    private double timeBehindNext;
    private double timeIntoLap;
    private double trackEdge;
    private boolean underYellow;
    private String upgradePack;
    private double veFraction;
    private String vehicleFilename;
    private String vehicleName;

    public StandingDTO() {
    }

    public AttackModeDTO getAttackMode() {
        return attackMode;
    }

    public void setAttackMode(AttackModeDTO attackMode) {
        this.attackMode = attackMode;
    }

    public double getBestLapSectorTime1() {
        return bestLapSectorTime1;
    }

    public void setBestLapSectorTime1(double bestLapSectorTime1) {
        this.bestLapSectorTime1 = bestLapSectorTime1;
    }

    public double getBestLapSectorTime2() {
        return bestLapSectorTime2;
    }

    public void setBestLapSectorTime2(double bestLapSectorTime2) {
        this.bestLapSectorTime2 = bestLapSectorTime2;
    }

    public double getBestLapTime() {
        return bestLapTime;
    }

    public void setBestLapTime(double bestLapTime) {
        this.bestLapTime = bestLapTime;
    }

    public double getBestSectorTime1() {
        return bestSectorTime1;
    }

    public void setBestSectorTime1(double bestSectorTime1) {
        this.bestSectorTime1 = bestSectorTime1;
    }

    public double getBestSectorTime2() {
        return bestSectorTime2;
    }

    public void setBestSectorTime2(double bestSectorTime2) {
        this.bestSectorTime2 = bestSectorTime2;
    }

    public VectorDTO getCarAcceleration() {
        return carAcceleration;
    }

    public void setCarAcceleration(VectorDTO carAcceleration) {
        this.carAcceleration = carAcceleration;
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public CarPositionDTO getCarPosition() {
        return carPosition;
    }

    public void setCarPosition(CarPositionDTO carPosition) {
        this.carPosition = carPosition;
    }

    public VectorDTO getCarVelocity() {
        return carVelocity;
    }

    public void setCarVelocity(VectorDTO carVelocity) {
        this.carVelocity = carVelocity;
    }

    public String getCountLapFlag() {
        return countLapFlag;
    }

    public void setCountLapFlag(String countLapFlag) {
        this.countLapFlag = countLapFlag;
    }

    public double getCurrentSectorTime1() {
        return currentSectorTime1;
    }

    public void setCurrentSectorTime1(double currentSectorTime1) {
        this.currentSectorTime1 = currentSectorTime1;
    }

    public double getCurrentSectorTime2() {
        return currentSectorTime2;
    }

    public void setCurrentSectorTime2(double currentSectorTime2) {
        this.currentSectorTime2 = currentSectorTime2;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public boolean isDrsActive() {
        return drsActive;
    }

    public void setDrsActive(boolean drsActive) {
        this.drsActive = drsActive;
    }

    public double getEstimatedLapTime() {
        return estimatedLapTime;
    }

    public void setEstimatedLapTime(double estimatedLapTime) {
        this.estimatedLapTime = estimatedLapTime;
    }

    public String getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(String finishStatus) {
        this.finishStatus = finishStatus;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public double getFuelFraction() {
        return fuelFraction;
    }

    public void setFuelFraction(double fuelFraction) {
        this.fuelFraction = fuelFraction;
    }

    public String getFullTeamName() {
        return fullTeamName;
    }

    public void setFullTeamName(String fullTeamName) {
        this.fullTeamName = fullTeamName;
    }

    public String getGamePhase() {
        return gamePhase;
    }

    public void setGamePhase(String gamePhase) {
        this.gamePhase = gamePhase;
    }

    public boolean isHasFocus() {
        return hasFocus;
    }

    public void setHasFocus(boolean hasFocus) {
        this.hasFocus = hasFocus;
    }

    public boolean isHeadlights() {
        return headlights;
    }

    public void setHeadlights(boolean headlights) {
        this.headlights = headlights;
    }

    public int getInControl() {
        return inControl;
    }

    public void setInControl(int inControl) {
        this.inControl = inControl;
    }

    public boolean isInGarageStall() {
        return inGarageStall;
    }

    public void setInGarageStall(boolean inGarageStall) {
        this.inGarageStall = inGarageStall;
    }

    public double getLapDistance() {
        return lapDistance;
    }

    public void setLapDistance(double lapDistance) {
        this.lapDistance = lapDistance;
    }

    public double getLapStartET() {
        return lapStartET;
    }

    public void setLapStartET(double lapStartET) {
        this.lapStartET = lapStartET;
    }

    public int getLapsBehindClassLeader() {
        return lapsBehindClassLeader;
    }

    public void setLapsBehindClassLeader(int lapsBehindClassLeader) {
        this.lapsBehindClassLeader = lapsBehindClassLeader;
    }

    public int getLapsBehindLeader() {
        return lapsBehindLeader;
    }

    public void setLapsBehindLeader(int lapsBehindLeader) {
        this.lapsBehindLeader = lapsBehindLeader;
    }

    public int getLapsBehindNext() {
        return lapsBehindNext;
    }

    public void setLapsBehindNext(int lapsBehindNext) {
        this.lapsBehindNext = lapsBehindNext;
    }

    public int getLapsCompleted() {
        return lapsCompleted;
    }

    public void setLapsCompleted(int lapsCompleted) {
        this.lapsCompleted = lapsCompleted;
    }

    public double getLastLapTime() {
        return lastLapTime;
    }

    public void setLastLapTime(double lastLapTime) {
        this.lastLapTime = lastLapTime;
    }

    public double getLastSectorTime1() {
        return lastSectorTime1;
    }

    public void setLastSectorTime1(double lastSectorTime1) {
        this.lastSectorTime1 = lastSectorTime1;
    }

    public double getLastSectorTime2() {
        return lastSectorTime2;
    }

    public void setLastSectorTime2(double lastSectorTime2) {
        this.lastSectorTime2 = lastSectorTime2;
    }

    public double getPathLateral() {
        return pathLateral;
    }

    public void setPathLateral(double pathLateral) {
        this.pathLateral = pathLateral;
    }

    public int getPenalties() {
        return penalties;
    }

    public void setPenalties(int penalties) {
        this.penalties = penalties;
    }

    public String getPitGroup() {
        return pitGroup;
    }

    public void setPitGroup(String pitGroup) {
        this.pitGroup = pitGroup;
    }

    public double getPitLapDistance() {
        return pitLapDistance;
    }

    public void setPitLapDistance(double pitLapDistance) {
        this.pitLapDistance = pitLapDistance;
    }

    public String getPitState() {
        return pitState;
    }

    public void setPitState(String pitState) {
        this.pitState = pitState;
    }

    public int getPitstops() {
        return pitstops;
    }

    public void setPitstops(int pitstops) {
        this.pitstops = pitstops;
    }

    public boolean isPitting() {
        return pitting;
    }

    public void setPitting(boolean pitting) {
        this.pitting = pitting;
    }

    public boolean isPlayer() {
        return player;
    }

    public void setPlayer(boolean player) {
        this.player = player;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getQualification() {
        return qualification;
    }

    public void setQualification(int qualification) {
        this.qualification = qualification;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public boolean isServerScored() {
        return serverScored;
    }

    public void setServerScored(boolean serverScored) {
        this.serverScored = serverScored;
    }

    public int getSlotID() {
        return slotID;
    }

    public void setSlotID(int slotID) {
        this.slotID = slotID;
    }

    public long getSteamID() {
        return steamID;
    }

    public void setSteamID(long steamID) {
        this.steamID = steamID;
    }

    public double getTimeBehindClassLeader() {
        return timeBehindClassLeader;
    }

    public void setTimeBehindClassLeader(double timeBehindClassLeader) {
        this.timeBehindClassLeader = timeBehindClassLeader;
    }

    public double getTimeBehindLeader() {
        return timeBehindLeader;
    }

    public void setTimeBehindLeader(double timeBehindLeader) {
        this.timeBehindLeader = timeBehindLeader;
    }

    public double getTimeBehindNext() {
        return timeBehindNext;
    }

    public void setTimeBehindNext(double timeBehindNext) {
        this.timeBehindNext = timeBehindNext;
    }

    public double getTimeIntoLap() {
        return timeIntoLap;
    }

    public void setTimeIntoLap(double timeIntoLap) {
        this.timeIntoLap = timeIntoLap;
    }

    public double getTrackEdge() {
        return trackEdge;
    }

    public void setTrackEdge(double trackEdge) {
        this.trackEdge = trackEdge;
    }

    public boolean isUnderYellow() {
        return underYellow;
    }

    public void setUnderYellow(boolean underYellow) {
        this.underYellow = underYellow;
    }

    public String getUpgradePack() {
        return upgradePack;
    }

    public void setUpgradePack(String upgradePack) {
        this.upgradePack = upgradePack;
    }

    public double getVeFraction() {
        return veFraction;
    }

    public void setVeFraction(double veFraction) {
        this.veFraction = veFraction;
    }

    public String getVehicleFilename() {
        return vehicleFilename;
    }

    public void setVehicleFilename(String vehicleFilename) {
        this.vehicleFilename = vehicleFilename;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
}