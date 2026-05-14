package com.newlifetechnologies.nltracingoverlay.dto;

public class ConsistencyDTO {

    private boolean active;
    private int lapCount;
    private String averageLap;
    private String bestRecentLap;
    private String variation;
    private String status;

    public ConsistencyDTO() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getLapCount() {
        return lapCount;
    }

    public void setLapCount(int lapCount) {
        this.lapCount = lapCount;
    }

    public String getAverageLap() {
        return averageLap;
    }

    public void setAverageLap(String averageLap) {
        this.averageLap = averageLap;
    }

    public String getBestRecentLap() {
        return bestRecentLap;
    }

    public void setBestRecentLap(String bestRecentLap) {
        this.bestRecentLap = bestRecentLap;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
