package com.newlifetechnologies.nltracingoverlay.formatter;

import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class OverlayFormatter {

    public String formatTime(double timeInSeconds) {
        if (timeInSeconds <= 0) {
            return "-";
        }

        int minutes = (int) (timeInSeconds / 60);
        double seconds = timeInSeconds % 60;

        return String.format(Locale.US, "%d:%06.3f", minutes, seconds);
    }
    
    public String formatRelativeInterval(double seconds, boolean ahead) {

        if (seconds <= 0) {
            return "-";
        }
        
        String signal = ahead ? "-" : "+";
        return String.format(Locale.US, "%s%.3f", signal, seconds);
    }
}