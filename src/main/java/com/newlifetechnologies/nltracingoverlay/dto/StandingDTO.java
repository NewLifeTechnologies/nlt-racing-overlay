package com.newlifetechnologies.nltracingoverlay.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StandingDTO {

    private boolean player;
    private double bestLapTime;
    private double fuelFraction;
    private double lastLapTime;
    private double veFraction;
    private int position;
    private String carId;
    private String driverName;
    private String vehicleName;

    public StandingDTO() {
    }

	public boolean isPlayer() {
		return player;
	}

	public void setPlayer(boolean player) {
		this.player = player;
	}

	public double getBestLapTime() {
		return bestLapTime;
	}

	public void setBestLapTime(double bestLapTime) {
		this.bestLapTime = bestLapTime;
	}

	public double getFuelFraction() {
		return fuelFraction;
	}

	public void setFuelFraction(double fuelFraction) {
		this.fuelFraction = fuelFraction;
	}

	public double getLastLapTime() {
		return lastLapTime;
	}

	public void setLastLapTime(double lastLapTime) {
		this.lastLapTime = lastLapTime;
	}
	
	public double getVeFraction() {
	    return veFraction;
	}

	public void setVeFraction(double veFraction) {
	    this.veFraction = veFraction;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
}