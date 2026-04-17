package com.newlifetechnologies.nltracingoverlay.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CarDisplayPropertiesDTO {

    private String displayName;
    private String fullTreePath;
    private String seriesName;
    private String carClass;
    private String carModel;

    public CarDisplayPropertiesDTO() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFullTreePath() {
        return fullTreePath;
    }

    public void setFullTreePath(String fullTreePath) {
        this.fullTreePath = fullTreePath;
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

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
}