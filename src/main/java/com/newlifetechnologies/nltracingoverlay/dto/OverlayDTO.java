package com.newlifetechnologies.nltracingoverlay.dto;

public class OverlayDTO {

    private String driverName;
    private int position;
    private String seriesName;
    private String carClass;
    private String vehicleName;
    private String bestLapTime;
    private String lastLapTime;
    private double fuelPercent;
    private double virtualEnergyPercent;
    private double fuelLiters;

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

    public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getCarClass() {
		return carClass;
	}

	public void setCarClass(String carClass) {
		this.carClass = carClass;
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

    public double getFuelPercent() {
        return fuelPercent;
    }

    public void setFuelPercent(double fuelPercent) {
        this.fuelPercent = fuelPercent;
    }
    
    public double getVirtualEnergyPercent() {
        return virtualEnergyPercent;
    }

    public void setVirtualEnergyPercent(double virtualEnergyPercent) {
        this.virtualEnergyPercent = virtualEnergyPercent;
    }

    public double getFuelLiters() {
        return fuelLiters;
    }

    public void setFuelLiters(double fuelLiters) {
        this.fuelLiters = fuelLiters;
    }
}