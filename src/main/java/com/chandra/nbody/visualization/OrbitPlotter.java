package com.chandra.nbody.visualization;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.markers.None;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OrbitPlotter {

    private static final String OUTPUT_FOLDER =
            "simulation-output";

    public OrbitPlotter() {

        new File(
                OUTPUT_FOLDER
        ).mkdirs();
    }

    public void plotSolarSystem(
            OrbitData sun,
            OrbitData earth,
            OrbitData moon,
            String title
    ) {

        XYChart chart =
                new XYChartBuilder()
                        .width(1200)
                        .height(800)
                        .title(title)
                        .xAxisTitle("X Position (m)")
                        .yAxisTitle("Y Position (m)")
                        .build();

        var sunSeries =
                chart.addSeries(
                        "Sun",
                        sun.getXPositions(),
                        sun.getYPositions()
                );

        var earthSeries =
                chart.addSeries(
                        "Earth",
                        earth.getXPositions(),
                        earth.getYPositions()
                );

        var moonSeries =
                chart.addSeries(
                        "Moon",
                        moon.getXPositions(),
                        moon.getYPositions()
                );

        sunSeries.setLineColor(Color.ORANGE);
        earthSeries.setLineColor(Color.BLUE);
        moonSeries.setLineColor(Color.CYAN);

        sunSeries.setMarker(new None());
        earthSeries.setMarker(new None());
        moonSeries.setMarker(new None());

        try {

            BitmapEncoder.saveBitmap(
                    chart,
                    OUTPUT_FOLDER
                            + "/solar-system-orbit",
                    BitmapEncoder.BitmapFormat.PNG
            );

            System.out.println(
                    "Saved -> "
                            + OUTPUT_FOLDER
                            + "/solar-system-orbit.png"
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new SwingWrapper<>(chart)
                .displayChart();
    }

    public void plotMoonRelativeOrbit(
            OrbitData moonRelativeOrbit
    ) {

        XYChart chart =
                new XYChartBuilder()
                        .width(1200)
                        .height(800)
                        .title(
                                "Moon Relative To Earth"
                        )
                        .xAxisTitle("X Position (m)")
                        .yAxisTitle("Y Position (m)")
                        .build();

        var moonSeries =
                chart.addSeries(
                        "Moon",
                        moonRelativeOrbit.getXPositions(),
                        moonRelativeOrbit.getYPositions()
                );

        moonSeries.setLineColor(Color.CYAN);
        moonSeries.setMarker(new None());

        try {

            BitmapEncoder.saveBitmap(
                    chart,
                    OUTPUT_FOLDER
                            + "/moon-relative-orbit",
                    BitmapEncoder.BitmapFormat.PNG
            );

            System.out.println(
                    "Saved -> "
                            + OUTPUT_FOLDER
                            + "/moon-relative-orbit.png"
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new SwingWrapper<>(chart)
                .displayChart();
    }

}