package com.chandra.nbody.app;

import com.chandra.nbody.integration.EulerIntegrator;
import com.chandra.nbody.integration.Integrator;
import com.chandra.nbody.model.Body;
import com.chandra.nbody.physics.PhysicsEngine;
import com.chandra.nbody.scenario.SolarSystemScenario;
import com.chandra.nbody.simulation.Simulation;

import java.util.List;

public class SimulationRunner {

    public static void main(String[] args) {

        double[] timeSteps = {
                1,
                10,
                100,
                1000
        };

        System.out.println(
                "---------------------------------------------------------------"
        );

        System.out.printf(
                "%-10s %-25s %-25s %-20s%n",
                "dt",
                "Initial Energy",
                "Final Energy",
                "Drift (%)"
        );

        System.out.println(
                "---------------------------------------------------------------"
        );

        for (double dt : timeSteps) {

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

            int totalSteps = 1000;

            for (int i = 0; i < totalSteps; i++) {
                simulation.step(dt);
            }

            double finalEnergy =
                    physicsEngine.calculateTotalEnergy(
                            bodies
                    );

            double driftPercentage =
                    Math.abs(
                            finalEnergy - initialEnergy
                    )
                            / Math.abs(initialEnergy)
                            * 100;

            System.out.printf(
                    "%-10.0f %-25.6e %-25.6e %-20.12e%n",
                    dt,
                    initialEnergy,
                    finalEnergy,
                    driftPercentage
            );
        }

        System.out.println(
                "---------------------------------------------------------------"
        );
    }
}