package com.chandra.nbody.app;

import com.chandra.nbody.integration.EulerIntegrator;
import com.chandra.nbody.integration.Integrator;
import com.chandra.nbody.integration.RK4Integrator;
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
                new RK4Integrator();

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
        List<Double> eulerDriftValues =
                new ArrayList<>();

        List<Double> rk4DriftValues =
                new ArrayList<>();

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
        // Euler vs RK4 Energy Drift
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

        System.out.println(
                "\n===== ENERGY DRIFT COMPARISON ====="
        );

        for (double currentDt : dtValues) {

            double eulerDrift =
                    calculateDrift(
                            currentDt,
                            new EulerIntegrator()
                    );

            double rk4Drift =
                    calculateDrift(
                            currentDt,
                            new RK4Integrator()
                    );
            eulerDriftValues.add(
                    eulerDrift
            );

            rk4DriftValues.add(
                    rk4Drift
            );

            System.out.println(
                    "dt = "
                            + currentDt
                            + " | Euler = "
                            + eulerDrift
                            + " | RK4 = "
                            + rk4Drift
            );
        }
        EnergyDriftPlotter driftPlotter =
                new EnergyDriftPlotter();

        driftPlotter.plotEnergyDriftComparison(
                dtValues,
                eulerDriftValues,
                rk4DriftValues
        );
    }

    private static double calculateDrift(
            double dt,
            Integrator integrator
    ) {

        List<Body> bodies =
                SolarSystemScenario.createBodies();

        PhysicsEngine physicsEngine =
                new PhysicsEngine();

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
                / Math.abs(initialEnergy)
                * 100;
    }

}