package com.newlifetechnologies.nltracingoverlay.dto;

public class RelativeCarDTO {

    private int position;
    private double virtualEnergyPercent;
    private String driverName;
    private String interval;
    private String carClass;

    public RelativeCarDTO() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getVirtualEnergyPercent() {
        return virtualEnergyPercent;
    }

    public void setVirtualEnergyPercent(double virtualEnergyPercent) {
        this.virtualEnergyPercent = virtualEnergyPercent;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }
}