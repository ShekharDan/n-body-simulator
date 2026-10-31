package com.chandra.nbody.visualization;

import java.util.ArrayList;
import java.util.List;

public class OrbitData {

    private final List<Double> xPositions =
            new ArrayList<>();

    private final List<Double> yPositions =
            new ArrayList<>();

    public void addPoint(
            double x,
            double y
    ) {
        xPositions.add(x);
        yPositions.add(y);
    }

    public List<Double> getXPositions() {
        return xPositions;
    }

    public List<Double> getYPositions() {
        return yPositions;
    }
}