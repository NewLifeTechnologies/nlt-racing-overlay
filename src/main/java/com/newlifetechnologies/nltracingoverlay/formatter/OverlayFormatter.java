package com.newlifetechnologies.nltracingoverlay.formatter;

import org.springframework.stereotype.Component;

@Component
public class OverlayFormatter {

    public String formatTime(double timeInSeconds) {
        if (timeInSeconds <= 0) {
            return "-";
        }

        int minutes = (int) (timeInSeconds / 60);
        double seconds = timeInSeconds % 60;

        return String.format("%d:%06.3f", minutes, seconds);
    }
}