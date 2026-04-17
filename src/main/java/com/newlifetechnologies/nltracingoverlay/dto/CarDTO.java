package com.newlifetechnologies.nltracingoverlay.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CarDTO {

    private String id;
    private String manufacturer;
    private String name;
    private String fullPathTree;
    private CarDisplayPropertiesDTO displayProperties;

    public CarDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullPathTree() {
        return fullPathTree;
    }

    public void setFullPathTree(String fullPathTree) {
        this.fullPathTree = fullPathTree;
    }

    public CarDisplayPropertiesDTO getDisplayProperties() {
        return displayProperties;
    }

    public void setDisplayProperties(CarDisplayPropertiesDTO displayProperties) {
        this.displayProperties = displayProperties;
    }
}