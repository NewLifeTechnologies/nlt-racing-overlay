package com.newlifetechnologies.nltracingoverlay.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StandingDTO {

    private String driverName;
    private boolean player;
    private int position;
    private String vehicleName;
    private double bestLapTime;
    private double lastLapTime;
    private double fuelFraction;

    public StandingDTO() {
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
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

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public double getBestLapTime() {
        return bestLapTime;
    }

    public void setBestLapTime(double bestLapTime) {
        this.bestLapTime = bestLapTime;
    }

    public double getLastLapTime() {
        return lastLapTime;
    }

    public void setLastLapTime(double lastLapTime) {
        this.lastLapTime = lastLapTime;
    }

    public double getFuelFraction() {
        return fuelFraction;
    }

    public void setFuelFraction(double fuelFraction) {
        this.fuelFraction = fuelFraction;
    }
}