package com.chandra.nbody.visualization;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.io.IOException;
import java.util.List;

public class EnergyDriftPlotter {
    public void plotEnergyDriftComparison(
            List<Double> dtValues,
            List<Double> eulerDriftValues,
            List<Double> rk4DriftValues
    ) {

        XYChart chart =
                new XYChartBuilder()
                        .width(1200)
                        .height(800)
                        .title("Euler vs RK4 Energy Drift")
                        .xAxisTitle("Timestep (dt)")
                        .yAxisTitle("Energy Drift (%)")
                        .build();

        chart.getStyler()
                .setXAxisLogarithmic(true);

        chart.getStyler()
                .setYAxisLogarithmic(true);

        chart.addSeries(
                "Euler",
                dtValues,
                eulerDriftValues
        );

        chart.addSeries(
                "RK4",
                dtValues,
                rk4DriftValues
        );

        try {

            BitmapEncoder.saveBitmap(
                    chart,
                    "simulation-output/euler-vs-rk4-energy-drift",
                    BitmapEncoder.BitmapFormat.PNG
            );

            System.out.println(
                    "Saved -> simulation-output/euler-vs-rk4-energy-drift.png"
            );

        } catch (IOException e) {

            throw new RuntimeException(e);
        }

        new SwingWrapper<>(chart)
                .displayChart();
    }

    public void plotEnergyDrift(
            List<Double> dtValues,
            List<Double> driftValues
    ) {

        XYChart chart =
                new XYChartBuilder()
                        .width(1200)
                        .height(800)
                        .title("Energy Drift vs Timestep (Log-Log Scale)")
                        .xAxisTitle("Timestep (dt)")
                        .yAxisTitle("Drift (%)")
                        .build();

        var series =
                chart.addSeries(
                        "Euler",
                        dtValues,
                        driftValues
                );

        series.setMarker(
                SeriesMarkers.CIRCLE
        );

        chart.getStyler()
                .setXAxisLogarithmic(true);

        chart.getStyler()
                .setYAxisLogarithmic(true);

        chart.getStyler()
                .setLegendVisible(false);
        try {

            BitmapEncoder.saveBitmap(
                    chart,
                    "simulation-output/energy-drift",
                    BitmapEncoder.BitmapFormat.PNG
            );

            System.out.println(
                    "Saved -> simulation-output/energy-drift.png"
            );

        } catch (IOException e) {

            throw new RuntimeException(e);
        }

        new SwingWrapper<>(chart)
                .displayChart();
    }
}