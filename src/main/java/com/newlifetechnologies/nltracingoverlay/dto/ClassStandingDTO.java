package com.newlifetechnologies.nltracingoverlay.dto;

public class ClassStandingDTO {

    private String carClass;
    private int classPosition;
    private int overallPosition;

    private String carId;
    private String carNumber;
    private String driverName;
    private String vehicleName;
    
    private boolean player;
    private boolean focus;

    private double timeBehindNext;
    private double timeBehindClassLeader;
    
    private String lastLapTime;
    private String bestLapTime;
    private boolean bestLapInClass;

    public ClassStandingDTO() {
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

    public int getClassPosition() {
        return classPosition;
    }

    public void setClassPosition(int classPosition) {
        this.classPosition = classPosition;
    }

    public int getOverallPosition() {
        return overallPosition;
    }

    public void setOverallPosition(int overallPosition) {
        this.overallPosition = overallPosition;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
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

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
    
    public boolean isPlayer() {
		return player;
	}

	public void setPlayer(boolean player) {
		this.player = player;
	}

	public boolean isFocus() {
		return focus;
	}

	public void setFocus(boolean focus) {
		this.focus = focus;
	}

	public double getTimeBehindNext() {
        return timeBehindNext;
    }

    public void setTimeBehindNext(double timeBehindNext) {
        this.timeBehindNext = timeBehindNext;
    }

    public double getTimeBehindClassLeader() {
        return timeBehindClassLeader;
    }

    public void setTimeBehindClassLeader(double timeBehindClassLeader) {
        this.timeBehindClassLeader = timeBehindClassLeader;
    }
    
    public String getLastLapTime() {
        return lastLapTime;
    }

    public void setLastLapTime(String lastLapTime) {
        this.lastLapTime = lastLapTime;
    }

    public String getBestLapTime() {
        return bestLapTime;
    }

    public void setBestLapTime(String bestLapTime) {
        this.bestLapTime = bestLapTime;
    }
    
    public boolean isBestLapInClass() {
        return bestLapInClass;
    }

    public void setBestLapInClass(boolean bestLapInClass) {
        this.bestLapInClass = bestLapInClass;
    }
}
