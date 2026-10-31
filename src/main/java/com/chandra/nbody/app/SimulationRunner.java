package com.chandra.nbody.app;

import com.chandra.nbody.integration.EulerIntegrator;
import com.chandra.nbody.integration.Integrator;
import com.chandra.nbody.model.Body;
import com.chandra.nbody.physics.PhysicsEngine;
import com.chandra.nbody.scenario.SolarSystemScenario;
import com.chandra.nbody.simulation.Simulation;
import com.chandra.nbody.visualization.EnergyDriftPlotter;
import com.chandra.nbody.visualization.OrbitData;
import com.chandra.nbody.visualization.OrbitPlotter;

import java.util.ArrayList;
import java.util.List;

public class SimulationRunner {

    public static void main(String[] args) {

        // -----------------------------
        // Orbit Visualizations
        // -----------------------------

        List<Body> bodies =
                SolarSystemScenario.createBodies();

        PhysicsEngine physicsEngine =
                new PhysicsEngine();

        Integrator integrator =
                new EulerIntegrator();

        Simulation simulation =
                new Simulation(
                        bodies,
                        physicsEngine,
                        integrator
                );

        Body sun = bodies.get(0);
        Body earth = bodies.get(1);
        Body moon = bodies.get(2);

        OrbitData sunOrbit =
                new OrbitData();

        OrbitData earthOrbit =
                new OrbitData();

        OrbitData moonOrbit =
                new OrbitData();

        OrbitData moonRelativeOrbit =
                new OrbitData();

        double dt =
                24 * 60 * 60;

        int totalSteps =
                365;

        for (int day = 0;
             day < totalSteps;
             day++) {

            simulation.step(dt);

            sunOrbit.addPoint(
                    sun.getPosition().getX(),
                    sun.getPosition().getY()
            );

            earthOrbit.addPoint(
                    earth.getPosition().getX(),
                    earth.getPosition().getY()
            );

            moonOrbit.addPoint(
                    moon.getPosition().getX(),
                    moon.getPosition().getY()
            );

            moonRelativeOrbit.addPoint(
                    moon.getPosition().getX()
                            - earth.getPosition().getX(),
                    moon.getPosition().getY()
                            - earth.getPosition().getY()
            );
        }

        OrbitPlotter plotter =
                new OrbitPlotter();

        plotter.plotSolarSystem(
                sunOrbit,
                earthOrbit,
                moonOrbit,
                "Solar System - One Year"
        );

        plotter.plotMoonRelativeOrbit(
                moonRelativeOrbit
        );

        // -----------------------------
        // Energy Drift Experiment
        // -----------------------------

        List<Double> dtValues =
                List.of(
                        1.0,
                        2.0,
                        5.0,
                        10.0,
                        20.0,
                        50.0,
                        100.0,
                        200.0,
                        500.0,
                        1000.0
                );

        List<Double> driftValues =
                new ArrayList<>();

        for (double currentDt : dtValues) {

            double drift =
                    calculateDrift(
                            currentDt
                    );

            driftValues.add(
                    drift
            );

            System.out.println(
                    "dt = "
                            + currentDt
                            + " drift = "
                            + drift
            );
        }

        EnergyDriftPlotter driftPlotter =
                new EnergyDriftPlotter();

        driftPlotter.plotEnergyDrift(
                dtValues,
                driftValues
        );
    }

    private static double calculateDrift(
            double dt
    ) {

        List<Body> bodies =
                SolarSystemScenario.createBodies();

        PhysicsEngine physicsEngine =
                new PhysicsEngine();

        Integrator integrator =
                new EulerIntegrator();

        Simulation simulation =
                new Simulation(
                        bodies,
                        physicsEngine,
                        integrator
                );

        double initialEnergy =
                physicsEngine.calculateTotalEnergy(
                        bodies
                );

        for (int i = 0;
             i < 1000;
             i++) {

            simulation.step(dt);
        }

        double finalEnergy =
                physicsEngine.calculateTotalEnergy(
                        bodies
                );

        return Math.abs(
                finalEnergy - initialEnergy
        )
                / Math.abs(
                initialEnergy
        )
                * 100;
    }
}