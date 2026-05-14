package com.newlifetechnologies.nltracingoverlay.dto;

public class PressureBehindDTO {

    private boolean active;
    private String carNumber;
    private String driverName;
    private String gapSeconds;
    private String gainPerLap;
    private String lapsUntilReach;

    public PressureBehindDTO() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getGapSeconds() {
        return gapSeconds;
    }

    public void setGapSeconds(String gapSeconds) {
        this.gapSeconds = gapSeconds;
    }

    public String getGainPerLap() {
        return gainPerLap;
    }

    public void setGainPerLap(String gainPerLap) {
        this.gainPerLap = gainPerLap;
    }

    public String getLapsUntilReach() {
        return lapsUntilReach;
    }

    public void setLapsUntilReach(String lapsUntilReach) {
        this.lapsUntilReach = lapsUntilReach;
    }
}
