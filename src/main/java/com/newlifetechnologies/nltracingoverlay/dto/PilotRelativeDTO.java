package com.newlifetechnologies.nltracingoverlay.dto;

public class PilotRelativeDTO {

    private PilotRelativeCarDTO ahead;
    private PilotRelativeCarDTO behind;

    public PilotRelativeDTO() {
    }

    public PilotRelativeCarDTO getAhead() {
        return ahead;
    }

    public void setAhead(PilotRelativeCarDTO ahead) {
        this.ahead = ahead;
    }

    public PilotRelativeCarDTO getBehind() {
        return behind;
    }

    public void setBehind(PilotRelativeCarDTO behind) {
        this.behind = behind;
    }
}
