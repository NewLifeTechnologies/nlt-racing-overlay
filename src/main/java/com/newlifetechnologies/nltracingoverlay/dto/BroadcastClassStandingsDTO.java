package com.newlifetechnologies.nltracingoverlay.dto;

import java.util.List;

public class BroadcastClassStandingsDTO {

    private String selectedClass;
    private List<ClassStandingDTO> standings;

    public BroadcastClassStandingsDTO() {
    }

    public BroadcastClassStandingsDTO(String selectedClass, List<ClassStandingDTO> standings) {
        this.selectedClass = selectedClass;
        this.standings = standings;
    }

    public String getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(String selectedClass) {
        this.selectedClass = selectedClass;
    }

    public List<ClassStandingDTO> getStandings() {
        return standings;
    }

    public void setStandings(List<ClassStandingDTO> standings) {
        this.standings = standings;
    }
}