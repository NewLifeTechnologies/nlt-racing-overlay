package com.newlifetechnologies.nltracingoverlay.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class LapHistoryService {

    private static final int MAX_HISTORY = 5;
    private static final double OUTLIER_FACTOR = 1.10;

    private int lastLapsCompleted = -1;
    private final List<Double> lapTimes = new ArrayList<>();

    public void record(int lapsCompleted, double lastLapTime) {
        if (lapsCompleted <= lastLapsCompleted || lastLapTime <= 0) {
            return;
        }

        lastLapsCompleted = lapsCompleted;

        if (!lapTimes.isEmpty()) {
            double mean = lapTimes.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            if (lastLapTime > mean * OUTLIER_FACTOR) {
                return;
            }
        }

        lapTimes.add(lastLapTime);
        if (lapTimes.size() > MAX_HISTORY) {
            lapTimes.remove(0);
        }
    }

    public List<Double> getHistory() {
        return Collections.unmodifiableList(lapTimes);
    }

    public void reset() {
        lastLapsCompleted = -1;
        lapTimes.clear();
    }
}
