package com.newlifetechnologies.nltracingoverlay.dto;

public class PilotRelativeCarDTO {

    private int position;
    private String carNumber;
    private String driverName;
    private String carClass;
    private String interval;
    private boolean sameClass;
    private String lapContext;
    private String lastLapTime;
    private String paceGap;
    private String threatStatus;

    public PilotRelativeCarDTO() {
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

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public boolean isSameClass() {
        return sameClass;
    }

    public void setSameClass(boolean sameClass) {
        this.sameClass = sameClass;
    }

    public String getLapContext() {
        return lapContext;
    }

    public void setLapContext(String lapContext) {
        this.lapContext = lapContext;
    }

    public String getLastLapTime() {
        return lastLapTime;
    }

    public void setLastLapTime(String lastLapTime) {
        this.lastLapTime = lastLapTime;
    }

    public String getPaceGap() {
        return paceGap;
    }

    public void setPaceGap(String paceGap) {
        this.paceGap = paceGap;
    }

    public String getThreatStatus() {
        return threatStatus;
    }

    public void setThreatStatus(String threatStatus) {
        this.threatStatus = threatStatus;
    }
}
