package com.newlifetechnologies.nltracingoverlay.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GapHistoryService {

    private static final int MAX_HISTORY = 5;

    private String trackedCarId = null;
    private int lastLapsCompleted = -1;
    private final List<Double> gapHistory = new ArrayList<>();

    public void record(String carId, int lapsCompleted, double gap) {
        if (carId == null) {
            return;
        }

        if (!carId.equals(trackedCarId)) {
            trackedCarId = carId;
            lastLapsCompleted = -1;
            gapHistory.clear();
        }

        if (lapsCompleted > lastLapsCompleted) {
            lastLapsCompleted = lapsCompleted;
            gapHistory.add(gap);
            if (gapHistory.size() > MAX_HISTORY) {
                gapHistory.remove(0);
            }
        }
    }

    public List<Double> getHistory() {
        return Collections.unmodifiableList(gapHistory);
    }

    public void reset() {
        trackedCarId = null;
        lastLapsCompleted = -1;
        gapHistory.clear();
    }
}
