package com.newlifetechnologies.nltracingoverlay.dto;

public class BroadcastRelativeDTO {

    private RelativeCarDTO ahead;
    private RelativeCarDTO focused;
    private RelativeCarDTO behind;

    public BroadcastRelativeDTO() {
    }

    public RelativeCarDTO getAhead() {
        return ahead;
    }

    public void setAhead(RelativeCarDTO ahead) {
        this.ahead = ahead;
    }

    public RelativeCarDTO getFocused() {
        return focused;
    }

    public void setFocused(RelativeCarDTO focused) {
        this.focused = focused;
    }

    public RelativeCarDTO getBehind() {
        return behind;
    }

    public void setBehind(RelativeCarDTO behind) {
        this.behind = behind;
    }
}