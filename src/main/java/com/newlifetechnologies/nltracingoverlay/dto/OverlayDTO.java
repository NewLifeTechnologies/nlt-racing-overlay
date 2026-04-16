package com.newlifetechnologies.nltracingoverlay.dto;

public class OverlayDTO {

    private String driverName;
    private int position;
    private String vehicleName;
    private String bestLapTime;
    private String lastLapTime;
    private int fuelPercent;

    public OverlayDTO() {
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
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

    public String getBestLapTime() {
        return bestLapTime;
    }

    public void setBestLapTime(String bestLapTime) {
        this.bestLapTime = bestLapTime;
    }

    public String getLastLapTime() {
        return lastLapTime;
    }

    public void setLastLapTime(String lastLapTime) {
        this.lastLapTime = lastLapTime;
    }

    public int getFuelPercent() {
        return fuelPercent;
    }

    public void setFuelPercent(int fuelPercent) {
        this.fuelPercent = fuelPercent;
    }
}