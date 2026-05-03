package com.newlifetechnologies.nltracingoverlay.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AttackModeDTO {

    private int remainingCount;
    private double timeRemaining;
    private int totalCount;

    public AttackModeDTO() {
    }

    public int getRemainingCount() {
        return remainingCount;
    }

    public void setRemainingCount(int remainingCount) {
        this.remainingCount = remainingCount;
    }

    public double getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(double timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}