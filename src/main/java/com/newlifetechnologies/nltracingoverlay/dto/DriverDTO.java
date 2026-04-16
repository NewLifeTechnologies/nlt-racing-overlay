package com.newlifetechnologies.nltracingoverlay.dto;

public class DriverDTO {

    private String name;
    private int position;

    public DriverDTO(String name, int position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}