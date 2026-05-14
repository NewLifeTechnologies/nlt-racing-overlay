package com.newlifetechnologies.nltracingoverlay.dto;

public class PressureAheadDTO {

    private boolean active;
    private int position;
    private String carNumber;
    private String driverName;
    private String gapSeconds;
    private String gainPerLap;
    private String lapsUntilAttack;

    public PressureAheadDTO() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public String getLapsUntilAttack() {
        return lapsUntilAttack;
    }

    public void setLapsUntilAttack(String lapsUntilAttack) {
        this.lapsUntilAttack = lapsUntilAttack;
    }
}
