package com.newlifetechnologies.nltracingoverlay.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PressureAheadHistoryService {

    private static final int MAX_HISTORY = 5;

    private String trackedCarId = null;
    private int lastLapsCompleted = -1;
    private final List<Double> gapHistory = new ArrayList<>();

    public void record(String aheadCarId, int playerLapsCompleted, double gap) {
        if (aheadCarId == null) {
            return;
        }

        if (!aheadCarId.equals(trackedCarId)) {
            trackedCarId = aheadCarId;
            lastLapsCompleted = -1;
            gapHistory.clear();
        }

        if (playerLapsCompleted > lastLapsCompleted) {
            lastLapsCompleted = playerLapsCompleted;
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
